package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
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

/**
 * Read an Excel sheet specified by fileConfigKey referencing to a {@link FileConfig} configuration.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 08:02:52 CET 2020
 */
public class XlsxReadCall extends FileReadCall implements ListParamsBeanInterface {
    public static final String LIST_PARAMS = "listParams";
    private ListParamsBean listParams;

    public XlsxReadCall() {
        super();
        listParams = new ListParamsBean();
    }

    public XlsxReadCall(final String configKey) {
        super(configKey);
        listParams = new ListParamsBean();
    }

    @Override
    public Object execute(final IEOScalar eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List<Object> readRaw(final IEOScalar eo) {
        FileConfig config = init(PermissionType.READ, eo);
        getListParams().merge(config);
        List result = new ArrayList<>();
        Sheet sheet = getSheet(eo);
        if (sheet == null) {
            throw new EoException("The sheet for '" + getFileConfig().getNaturalId() + "' is null. Perhaps the sheet name '" + config.getProperties().getSheetName() + "' is undefined.");
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
    /*.{javaAccessors}|*/

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    public XlsxReadCall setListParams(ListParamsBean listParams) {
        this.listParams = listParams;
        return this;
    }

    @Override
    public ListParamsBean getListParams() {
        return this.listParams;
    }

    public boolean hasListParams() {
        return listParams != null;
    }

    public Sheet getSheet(final IEOScalar eo) {
        Workbook wb = readWorkbook(eo);
        if (!getFileConfig().getProperties().hasSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getFileConfig().getProperties().getSheetName());
        }
    }

    public Workbook readWorkbook(final IEOScalar eo) {
        URL url = getFileConfig().findUrl(eo, getHostConfigKey());
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
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date dateValue = cell.getDateCellValue();
                            rowValues.add(dateValue);
                        } else {
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
                        }
                        break;
                    default:
                        rowValues.add(null);
                }
            } catch (Exception e) {
                rowValues.add(null);
            }
        }
        if (containsData) {
            return rowValues;
        } else {
            return null;
        }
    }
}
