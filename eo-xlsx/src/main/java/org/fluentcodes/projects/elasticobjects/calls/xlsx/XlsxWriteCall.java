package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Write an Excel sheet specified by fileConfigKey referencing to a {@link FileConfig} configuration.
 */
public class XlsxWriteCall extends XlsxCall {
    Logger LOG = LoggerFactory.getLogger(XlsxWriteCall.class);
    private FileWriteCall fileWriteCall;


    public XlsxWriteCall() {
        super();
    }

    public XlsxWriteCall(final String fileConfigKey) {
        super(fileConfigKey);
    }

    public XlsxWriteCall(final String fileConfigKey, String fileName) {
        super(fileConfigKey, fileName);
    }

    @Override
    public String execute(EOInterfaceScalar eo) {
        super.init(eo);
        if (eo.isList()) {
            mergeColKeys(eo.getEo("0"));
        } else if (eo.isMap()) {
            String key = new ArrayList<String>(((EO) eo).keys()).get(0);
            mergeColKeys(eo.getEo(key));
        } else {
            throw new EoException("Nor List nor Map at path " + eo.getPathAsString());
        }
        if (eo.isEmpty()) {
            throw new EoException("No values at path " + eo.getPathAsString());
        }
        XSSFWorkbook workbook = create(eo);

        String fileName = super.getUrlPath();
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public FileWriteCall getFileWriteCall() {
        return fileWriteCall;
    }

    @Override
    public void setByParameter(String values) {

    }

    public XSSFWorkbook create(EOInterfaceScalar eo) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(fetchSheetName());
        if (hasRowHead()) {
            for (int i = 0; i < getListParams().getRowHead(); i++) {
                Row row = sheet.createRow(i);
            }
            Row row = sheet.createRow(getListParams().getRowHead());
            for (int i = 0; i < getColKeys().size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(getColKeys().get(i));
            }
        }

        int currentRow = getListParams().getRowStart();
        for (String key : ((EO) eo).keys()) {
            Row row = sheet.createRow(currentRow);
            EOInterfaceScalar rowValues = eo.getEo(key);
            addRow(row, rowValues);
            currentRow++;
        }
        LOG.info("Written " + currentRow + " entries.");
        return workbook;
    }

    private void addRow(Row row, EOInterfaceScalar rowValues) {
        if (rowValues.isEmpty()) {
            return;
        }
        for (int i = 0; i < getColKeys().size(); i++) {
            String key = getColKeys().get(i);
            if (!rowValues.hasEo(key)) {
                continue;
            }
            EOInterfaceScalar entry = rowValues.getEo(key);
            Cell cell = row.createCell(i);
            if (entry.isEmpty()) {
                //buffer.append(fieldDelimiter);
                continue;
            }
            if (entry.isScalar()) {
                if (entry.getModelClass() == LocalDateTime.class) {
                    cell.setCellValue((LocalDateTime) entry.get());
                } else if (entry.getModelClass() == Date.class) {
                    cell.setCellValue((Date) entry.get());
                } else if (entry.getModelClass() == String.class) {
                    cell.setCellValue((String) entry.get());
                } else if (entry.getModelClass() == Boolean.class) {
                    cell.setCellValue((Boolean) entry.get());
                } else if (entry.getModelClass() == Double.class) {
                    cell.setCellValue((Double) entry.get());
                } else if (entry.getModelClass() == Float.class) {
                    double value = ((Float) entry.get()).doubleValue();
                    cell.setCellValue(value);
                }
            } else {
                cell.setCellValue(new EOToJSON(JSONSerializationType.STANDARD).toJson(entry));
            }
        }
    }

}
