package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.4.2017
 */
public class ListParamsTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ListParamsBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void getSetRowHead() {
        assertSetGet(ListParamsBean.ROW_HEAD, 1);
    }

    @Test
    public void callCheckRowStart() {
        final ListParamsBean bean = new ListParamsBean();
        bean.setRowHead(3);
        Assert.assertEquals(new Integer(4), bean.checkRowStart().getRowStart());
    }

    @Ignore
    @Test
    public void checkObjectSetRowStart() {
        ListParamsBean params = new ListParamsBean();
        params.setRowStart(5);
        Assert.assertEquals(new Integer(5), params.getRowStart());
        params.setRowStart(6);
        Assert.assertEquals(new Integer(5), params.getRowStart());
        params.setDefault();
        Assert.assertEquals(new Integer(5), params.getRowStart());
        Assert.assertEquals(new Integer(-1), params.getRowHead());
        Assert.assertEquals(new Integer(-1), params.getLength());
        Assert.assertEquals(new Integer(-1), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(10), params.getRowEnd());
    }

    @Ignore
    @Test
    public void checkObjectSetLength() {
        ListParamsBean params = new ListParamsBean();
        params.setLength(5);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.setLength(6);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.setDefault();
        Assert.assertEquals(new Integer(0), params.getRowStart());
        Assert.assertEquals(new Integer(-1), params.getRowHead());
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
        params.prepareStartEnd(3);
        Assert.assertEquals(new Integer(3), params.getLength());
        Assert.assertEquals(new Integer(3), params.getRowEnd());
    }

    @Test
    public void checkObjectSetHeadAndLength() {
        ListParamsBean params = new ListParamsBean();
        params.setRowHead(2);
        params.setLength(5);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.setLength(6);
        Assert.assertEquals(new Integer(6), params.getLength());
        params.setDefault();
        Assert.assertEquals(new Integer(3), params.getRowStart());
        Assert.assertEquals(new Integer(2), params.getRowHead());
        Assert.assertEquals(new Integer(6), params.getLength());
        Assert.assertEquals(new Integer(9), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(6), params.getLength());
        Assert.assertEquals(new Integer(9), params.getRowEnd());
        params.prepareStartEnd(5);
        Assert.assertEquals(new Integer(2), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
    }
}

