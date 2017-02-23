package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.sf.json.JSONObject;

public class JSONUtil {
	
	/**
	 * 从文件加载json
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject load(String path) throws Exception{
		JSONObject jsonObject = null;
		BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
		try{
			StringBuilder builder = new StringBuilder();
			reader = new BufferedReader(new FileReader(new File(path)));
			String str = null;
			while (null!=(str=reader.readLine())) {
				System.out.println(str);
				builder.append(str);
			}
			jsonObject = (str = builder.toString())==null?null:JSONObject.fromObject(str);
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			if(reader!=null)reader.close();
		}
		
		return jsonObject;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(load("D:/aa.txt"));
	}
}
