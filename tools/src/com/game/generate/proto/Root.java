package com.game.generate.proto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/3/7.
 */
@XmlRootElement(name="root")
public class Root
{
    private List<ModuleInfo> infos;

    public List<ModuleInfo> getInfos()
    {
        return infos;
    }

    @XmlElement(name = "module")
    public void setInfos(List<ModuleInfo> infos)
    {
        this.infos = infos;
    }
}
