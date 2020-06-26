package net.lz1998.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response<T> {
    /**
     * 状态码
     * 正确大于0，绿色提示
     * 正确等于0，不提示
     * 错误小于0，红色提示
     */
    private Integer code;
    /**
     * 正确/错误提示
     */
    private String msg;


    private T data;

    /**
     * 获取不提示数据
     *
     * @param data 结果
     * @return 响应对象
     */
    public static Response getResponse(Object data) {
        return new Response(0, "ok", data);
    }

    /**
     * 获取失败数据（红色提示）
     *
     * @param code 状态码，必须小于0
     * @param msg  提示内容
     * @param data 结果
     * @return 响应对象
     */
    public static Response getFailResponse(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    /**
     * 获取成功数据（绿色提示）
     *
     * @param code 状态码，必须大于0
     * @param msg  提示内容
     * @param data 结果
     * @return 响应对象
     */
    public static Response getSuccessResponse(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }
}