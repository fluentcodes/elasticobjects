package org.fluentcodes.projects.elasticobjects.calls.files;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsConfigInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Werner on 30.10.2016.
 */
public class XlsxConfig extends FileConfig implements XlsxInterface, ListParamsConfigInterface {
    private final ListParamsConfig listParams;
    private final String sheetName;

    public XlsxConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((FileBean) configBean, configMaps);
    }

    public XlsxConfig(final FileBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.listParams = new ListParamsConfig(bean.getProperties());
        this.sheetName = new ShapeTypeSerializerString().asObject(bean.getProperties().get(SHEET_NAME));
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    public Sheet getSheet(final IEOScalar eo) {
        Workbook wb = readWorkbook(eo);
        if (hasNoSheetName()) {
            return wb.getSheetAt(0);
        } else {
            return wb.getSheet(getSheetName());
        }
    }

    public boolean hasNoSheetName() {
        return getSheetName() == null || getSheetName().isEmpty();
    }

    public List readRaw(ListParamsBean params) {
        throw new EoException("Deprecated");
    }

    @Override
    public ListParamsConfig getListParamsConfig() {
        return listParams;
    }

    public Object read(CsvSimpleReadCall readCall) {
        throw new EoException("Deprecated");
    }

    public Workbook readWorkbook(final IEOScalar eo) {
        URL url = findUrl(eo, getHostConfigKey());
        if (url == null) {
            throw new EoException("Could not load url from " + getNaturalId());
        }
        InputStream inp = null;
        try {
            inp = url.openStream();
        } catch (IOException e) {
            throw new EoException(e);
        }

        Workbook wb = null;
        try {
            return WorkbookFactory.create(inp);
        } catch (IOException e) {
            throw new EoException(e);
        } catch (InvalidFormatException e) {
            throw new EoException(e);
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
            CellType cellType = cell.getCellTypeEnum();
            //dateFormatted=HSSFDateUtil.isCellDateFormatted(cell);
            //formulaResultType = cell.getCachedFormulaResultType();
            //https://stackoverflow.com/questions/7608511/java-poi-how-to-read-excel-cell-value-and-not-the-formula-computing-it
            try {
                if (cellType == CellType.STRING) {
                    String myValue = cell.getStringCellValue();
                    if (myValue != null && !myValue.isEmpty()) {
                        containsData = true;
                        rowValues.add(myValue);
                    } else {
                        rowValues.add(null);
                    }
                    continue;
                } else if (cellType == CellType.BOOLEAN) {
                    rowValues.add(cell.getBooleanCellValue());
                } else if (cellType != CellType.FORMULA && HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date dateValue = cell.getDateCellValue();
                    rowValues.add(dateValue);
                } else if (cellType == CellType.NUMERIC) {
                    Double doubleValue = cell.getNumericCellValue();
                    rowValues.add(doubleValue);
                } else if (cellType == CellType.BLANK) {
                    rowValues.add(null);
                } else if (cellType == CellType.FORMULA) {
                    switch (cell.getCachedFormulaResultType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                rowValues.add(cell.getDateCellValue());
                            } else {
                                rowValues.add(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowValues.add(cell.getStringCellValue());
                            break;
                    }
                } else {
                    //http://apache-poi.1045710.n5.nabble.com/CELL-TYPE-FORMULA-String-vs-Numeric-td2304091.html
                    String formulaValue = cell.getStringCellValue();
                    rowValues.add(formulaValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                rowValues.add(null);
            }
        }
        if (containsData) {
            return rowValues;
        } else {
            return null;
        }
    }

    public void write(final IEOObject eo, List rows) {
        Workbook wb = null;
        Sheet sheet = null;
        try {
            sheet = getSheet(eo);
        } catch (Exception e) {
            throw new EoException(e);
        }
        int rowCounter = -1;
        for (Object row : rows) {
            rowCounter++;
            if (row == null) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            if (!(row instanceof List)) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            if (((List) row).isEmpty()) {
                Row xlsxRow = sheet.createRow(rowCounter);
                continue;
            }
            Row xlsxRow = sheet.getRow(rowCounter);
            if (xlsxRow == null) {
                xlsxRow = sheet.createRow(rowCounter);
            }
            List values = (List) row;
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                Cell cell = xlsxRow.createCell(i);
                try {
                    cell.setCellValue(new ShapeTypeSerializerString().asObject(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            writeWorkbook(eo, sheet.getWorkbook());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeWorkbook(final IEOObject eo, Workbook wb) {
        URL url = findUrl(eo, getHostConfigKey());
        //URLConnection connection = url.openConnection();

        //if (connection==null) {
        //    throw new Exception ("Could not load url from " + url.getFile());
        //}

        //file.createNewFile();

        try {
            OutputStream out = null;
            try {
                out = new FileOutputStream(url.getFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            wb.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new EoException(e);
        }
    }

    //https://poi.apache.org/spreadsheet/quick-guide.html#NewWorkbook
    protected Sheet createSheet(final Workbook wb) {
        if (getSheetName().isEmpty()) {
            return wb.createSheet(new Integer(wb.getNumberOfSheets()).toString());
        } else {
            return wb.createSheet(getSheetName());
        }
    }


}
