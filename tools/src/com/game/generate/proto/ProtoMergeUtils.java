/**
 * 
 */
package com.game.generate.proto;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lip.li
 * @date 2017-1-13
 */
public class ProtoMergeUtils
{

	public static List<String>  modify(File file )throws Exception{
		List<String> oldRows=FileUtils.readLines(file, "utf8");
		List<String> newRows=new ArrayList<String>();

		String className=file.getName().split("\\.")[0];
		String packageName="";

		for(String oldRow:oldRows){
			if(oldRow.contains("java_package")){
				packageName=StringUtils.substringBetween(oldRow, "\"", "\"");
				continue;
			}

			if(oldRow.contains("java_outer_classname")){
				className=StringUtils.substringBetween(oldRow, "\"", "\"");
				continue;
			}

			if(StringUtils.trim(oldRow).contains("package")){
				continue;
			}

			if(StringUtils.trim(oldRow).contains("import")){
				continue;
			}

			if(oldRow.contains("message")){
				String [] arrays=oldRow.trim().split(" ");

				int index=0;
				for(int i=0;i<arrays.length;i++){
					if(arrays[i].contains("message")){
						index++;
						continue;
					}
				}
				String subClass=arrays[index].trim();
				oldRow=oldRow.replace(subClass, packageName+"."+className+"$"+subClass);
			}

			newRows.add(oldRow);

		}


		return newRows;
	}
}
