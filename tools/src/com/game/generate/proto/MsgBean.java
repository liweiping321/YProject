package com.game.generate.proto;

import javax.xml.bind.annotation.*;

/**
 * @author jianpeng.zhang
 * @since 2017/3/6.
 */
@XmlRootElement(name="ProtoConfig")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class MsgBean
{
    /**
     *  UP 或 DOWN
     */
    private String type;
    /**
     * 消息号
     */
    private int code;

    private String name;

    private int subCode = 0;

    private String protocolType = "PB";

    private String desc;

    private String bodyClazzName = "";

    private String msgBody;

    private String template;

    public int getCode()
    {
        return code;
    }

    @XmlAttribute(name = "code")
    public void setCode(int code)
    {
        this.code = code;
    }

    public String getDesc()
    {
        return desc;
    }

    @XmlAttribute(name = "desc")
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getType()
    {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setType(String type)
    {
        this.type = type;
    }

    public String getBodyClazzName()
    {
        return bodyClazzName;
    }

    @XmlAttribute
    public void setBodyClazzName(String bodyClazzName)
    {
        this.bodyClazzName = bodyClazzName;
    }

    public int getSubCode()
    {
        return subCode;
    }

    @XmlAttribute
    public void setSubCode(int subCode)
    {
        this.subCode = subCode;
    }

    public String getProtocolType()
    {
        return protocolType;
    }

    @XmlAttribute
    public void setProtocolType(String protocolType)
    {
        this.protocolType = protocolType;
    }

    public String getName()
    {
        return name;
    }

    @XmlTransient
    public void setName(String name)
    {
        this.name = name;
    }

    public String getMsgBody()
    {
        return msgBody;
    }

    @XmlTransient
    public void setMsgBody(String msgBody)
    {
        this.msgBody = msgBody;
    }

    public String getTemplate()
    {
        return template;
    }

    @XmlTransient
    public void setTemplate(String template)
    {
        this.template = template;
    }
}
