package web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import util.TemplateServiceUtil;

/**
 * Servlet implementation class TemplateServlet
 */
public class TemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TemplateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String path = request.getServletContext().getRealPath("/data/xjzs1.json");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("zsbh", "cz0909090909");
		map.put("yxqzkzn", "2500");
		map.put("jddc", "鲶鱼客运");
		map.put("gsmcyw", "nianyukeyunchuzu");
		map.put("fzrqkzr", "15");
		map.put("yxqzr", "16");
		map.put("fzrqkzy", "05");
		map.put("gszcd", "鲶鱼村鲶鱼可");
		map.put("ZZEWM", "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=json%20lib&oq=json%25E5%259C%25A8%25E7%25BA%25BF%25E8%25A7%25A3%25E6%259E%2590&rsv_pq=db2a3f1500004c35&rsv_t=f4381JBlV8nYXFw19RcQNLJawTkmRe6SF%2BzpTR8nDXO2ib1mZ5lppUCokoM&rqlang=cn&rsv_enter=1&rsv_sug1=6&rsv_sug7=100&rsv_sug3=8&bs=json%E5%9C%A8%E7%BA%BF%E8%A7%A3%E6%9E%90");
		map.put("fzrqkzn", "2017");
		map.put("yxqzkzy", "06");
		map.put("ZZYWM", "110120130140150160");
		
		try {
			response.getWriter().write(TemplateServiceUtil.setVal(JSONUtil.load(path), map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
