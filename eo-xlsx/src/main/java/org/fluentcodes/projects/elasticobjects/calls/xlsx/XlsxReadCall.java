package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.apache.poi.ss.usermodel.Sheet;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.XlsxConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*=>{javaHeader}|*/
/**
 * Read an Excel sheet specified by fileConfigKey referencing to a {@link XlsxConfig} configuration.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 08:02:52 CET 2020
 */
public class XlsxReadCall extends FileReadCall implements ListInterface {
/*=>{}.*/

/*=>{javaStaticNames}|*/
   public static final String LIST_PARAMS = "listParams";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   private ListParams listParams;
/*=>{}.*/
    public XlsxReadCall()  {
        super();
        listParams = new ListParams();
    }

    public XlsxReadCall(final String configKey)  {
        super(configKey);
        listParams = new ListParams();
    }

    public Object execute(EO eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final EO eo) {
        XlsxConfig config = (XlsxConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        List result = new ArrayList<>();
        Sheet sheet = config.getSheet(eo);
        if (sheet == null) {
            throw new EoException("The sheet for '" + getNaturalId() + "' is null. Perhaps the sheet name '" + config.getSheetName() + "' is undefined.");
        }
        List rowEntry;
        int i = -1;
        while((rowEntry = config.getRowAsList(sheet.getRow(i+1))) != null) {
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
                getListParams().addRowEntry(eo.getConfigsCache(), result, rowEntry);
            }
            catch (Exception e) {
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
/*=>{javaAccessors}|*/
    /**
    Parameters of type {@link ListParams} for list type read call operations like {@link CsvSimpleReadCall}.
    */
    @Override
    public XlsxReadCall setListParams(ListParams listParams) {
        this.listParams = listParams;
        return this;
    }
    @Override
    public ListParams getListParams () {
       return this.listParams;
    }
    @Override
    public boolean hasListParams () {
        return listParams!= null;
    }
/*=>{}.*/
}
