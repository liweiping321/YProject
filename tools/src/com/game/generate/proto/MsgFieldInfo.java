package com.game.generate.proto;

import java.util.ArrayList;

/**
 * @author jianpeng.zhang
 * @since 2017/3/8.
 */
public class MsgFieldInfo
{
    private String fieldName;

    private String comment;

    private int code;

    private ArrayList<MsgFieldInfo> fieldList = new ArrayList<>();

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public ArrayList<MsgFieldInfo> getFieldList()
    {
        return fieldList;
    }

    public void setFieldList(ArrayList<MsgFieldInfo> fieldList)
    {
        this.fieldList = fieldList;
    }
}
