package org.fluentcodes.projects.elasticobjects.calls.csv;

import au.com.bytecode.opencsv.CSVReader;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Defines read call for a csv action depending on one link CsvConfig.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:03:07 CET 2020
 */
public class CsvReadCall extends CsvSimpleReadCall {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/
    public CsvReadCall() {
        super();
    }

    public CsvReadCall(final String configKey) {
        super(configKey);
    }

    @Override
    public List readRaw(final IEOScalar eo) {
        CsvConfig csvFileConfig = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(csvFileConfig.getListParams());
        URL url = csvFileConfig.findUrl(eo, getHostConfigKey());
        //System.out.println("CSV " + url.toString());
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(url.openStream()), csvFileConfig.getFieldDelimiter().charAt(0));
        } catch (IOException e) {
            throw new EoException(e);
        }
        List result = new ArrayList<>();
// https://stackoverflow.com/questions/10264054/assign-variable-in-java-while-loop-conditional
        try {
            String[] row = null;
            int i = 0;
            while ((row = reader.readNext()) != null) {
                i++;
                if (row == null || row.length == 0) {
                    continue;
                }
                if (getListParams().isRowHead(i)) {
                    if (!getListParams().hasColKeys()) {
                        getListParams().setColKeys(Arrays.asList(row));
                    }
                    continue;
                }
                if (!getListParams().isRowStart(i)) {
                    continue;
                }
                if (!getListParams().isRowEnd(i)) {
                    return result;
                }
                List rowEntry = Arrays.asList(row);
                getListParams().addRowEntry(eo.getConfigMaps(), result, rowEntry);
            }
        } catch (IOException e) {
            throw new EoException(e);
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new EoException(e);
            }
        }
        return result;
    }
    /*.{javaAccessors}|*/
    /*.{}.*/
}
