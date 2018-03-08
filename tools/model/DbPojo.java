package ${package};
 
import com.game.db.base.AbstractEntityBean;
import com.game.db.base.anno.EntityMap;
import com.game.db.base.anno.PropertyMap;
<#list imports as import>
import ${import};
</#list>

/**
 *@author 工具生成
 *@date ${date}
 */
@EntityMap(table = "${table}")
public class ${className} extends  AbstractEntityBean<${keyType},${className}>{
	
	<#list props as pro>
	/**${pro.columnComment}*/
	@PropertyMap(column="${pro.columnName}"<#if pro.generatedKey>,generatedKey=true</#if><#if pro.primarykey>,primarykey=true</#if>)
	private  ${pro.javaType} ${pro.attributeName};
    </#list>
	
    
    <#list props as pro>
    public void set${pro.upperattributeName}(${pro.javaType} ${pro.attributeName}){
        this.${pro.attributeName} = ${pro.attributeName};
    }

    public  ${pro.javaType} get${pro.upperattributeName}(){
        return ${pro.attributeName};
    }
    </#list>
}