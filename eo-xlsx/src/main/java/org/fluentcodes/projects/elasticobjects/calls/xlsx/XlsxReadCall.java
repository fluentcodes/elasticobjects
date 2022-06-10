package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Read an Excel sheet specified by fileConfigKey referencing to a {@link FileConfig} configuration.
 */
public class XlsxReadCall extends XlsxCall {
    public XlsxReadCall() {
        super();
    }

    public XlsxReadCall(final String fileConfigKey) {
        super(fileConfigKey);
    }

    public XlsxReadCall(final String fileConfigKey, final String fileName) {
        super(fileConfigKey, fileName);
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        try {
            EOInterfaceScalar targetEo = eo;
            /*if (hasTargetPath()) {
                targetEo = ((EoChild)eo).createChild(getTargetPath());
            }*/
            mapToTarget(targetEo, readRaw(eo));
            return "";
        }
        catch (MalformedURLException e) {
            eo.error(e.getMessage());
            return "";
        }
    }

    public List<Map<String,Object>> readRaw(final EOInterfaceScalar eo) throws MalformedURLException {
        init(eo);
        List result = new ArrayList<>();
        Sheet sheet = getSheet();
        if (sheet == null) {
            throw new EoException("The sheet for '" + getFileConfig().getNaturalId() + "' is null. Perhaps the sheet name '" + fetchSheetName() + "' is undefined.");
        }
        List rowEntry;
        int i = -1;
        while ((rowEntry = getRowAsList(sheet.getRow(i + 1))) != null) {
            i++;
            if (getListParams().isRowHead(i)) {
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(rowEntry);
                }
                continue;
            }
            if (!getListParams().isRowStart(i)) {
                continue;
            }
            if (!getListParams().isRowEnd(i)) {
                return result;
            }
            try {
                getListParams().addRowEntry(eo.getConfigMaps(), result, rowEntry);
            } catch (Exception e) {
                throw new EoInternalException("Problem with row " + i + ": " + rowEntry + "", e);
            }
        }

        try {
            sheet.getWorkbook().close();
        } catch (IOException e) {
            throw new EoException(e);
        }
        return result;
    }

    public Sheet getSheet() throws MalformedURLException {
        Workbook wb = createWorkbook();
        if (!hasSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getSheetName());
        }
    }

    public Workbook createWorkbook() throws MalformedURLException {
        String fileName = super.getUrlPath();
        File file = new File(fileName);
        if (!file.exists()) {
            throw new EoException("File not exists: " + file.getAbsolutePath());
        }
        URL url = file.toURI().toURL();
        if (url == null) {
            throw new EoException("Could not load url from " + getFileConfig().getNaturalId());
        }
        InputStream inp = null;
        try {
            inp = url.openStream();
        } catch (IOException e) {
            throw new EoException("Problem with open stream '" + url.toString() + "': " + e.getMessage());
        }

        Workbook wb = null;
        try {
            return WorkbookFactory.create(inp);
        } catch (Exception e) {
            throw new EoException("Problem create workbook from input stream '" + url.toString() + "': " + e.getMessage());
        }
    }

    public List getRowAsList(Row row) {
        if (row == null) {
            return null;
        }
        List rowValues = new ArrayList();
        boolean containsData = false;

        for (int i = 0; i < row.getLastCellNum(); i++) {
            final Cell cell = row.getCell(i);
            if (cell == null) {
                rowValues.add(null);
                continue;
            }
            String value = "";
            CellType cellType = cell.getCellType();
            //dateFormatted=HSSFDateUtil.isCellDateFormatted(cell);
            //formulaResultType = cell.getCachedFormulaResultType();
            //https://stackoverflow.com/questions/7608511/java-poi-how-to-read-excel-cell-value-and-not-the-formula-computing-it
            try {
                switch (cell.getCellType()) {
                    case STRING:
                        String myValue = cell.getStringCellValue();
                        if (myValue != null && !myValue.isEmpty()) {
                            containsData = true;
                            rowValues.add(myValue);
                        } else {
                            rowValues.add(null);
                        }
                        break;
                    case BOOLEAN:
                        rowValues.add(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        Double doubleValue = cell.getNumericCellValue();
                        rowValues.add(doubleValue);
                        break;
                    case BLANK:
                        rowValues.add(null);
                        break;
                    case FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        rowValues.add(cell.getDateCellValue());
                                    } else {
                                        rowValues.add(cell.getNumericCellValue());
                                    }
                                    break;
                                case STRING:
                                    rowValues.add(cell.getStringCellValue());
                                    break;
                                default:
                                    //http://apache-poi.1045710.n5.nabble.com/CELL-TYPE-FORMULA-String-vs-Numeric-td2304091.html
                                    String formulaValue = cell.getStringCellValue();
                                    rowValues.add(formulaValue);

                        }
                        break;
                    default:
                        rowValues.add(null);
                }
            } catch (Exception e) {
                rowValues.add(e.getMessage());
            }
        }
        if (containsData) {
            return rowValues;
        } else {
            return null;
        }
    }
}
