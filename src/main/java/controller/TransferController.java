package controller;

import factory.AnnotationBeanFactory;
import factory.BeanFactory;
import pojo.Result;
import service.impl.TransferServiceImpl;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "transferController",urlPatterns = "/transferServlet")
public class TransferController extends HttpServlet {
    private TransferServiceImpl transferService = (TransferServiceImpl) AnnotationBeanFactory.getBean("transferService");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);
        Result result = new Result();
        try {
            transferService.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
            result.setMessage("success");
        } catch (Exception e) {
            result.setStatus("500");
            result.setMessage("fail");
        }
        super.doPut(req, resp);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }
}
