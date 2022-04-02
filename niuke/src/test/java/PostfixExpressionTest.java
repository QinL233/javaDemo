import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author liaoqinzhou_sz
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年01月20日 18:20:00
 */
public class PostfixExpressionTest {

    @Test
    public void handler() {
        String exp = "1+2*3+(4*5+6)*7";//=189
        String result = infixTranPostfix(exp);
        System.out.println(result);
        String h = calculationPostfix(result);
        System.out.println(h);
    }

    /**
     * 中缀表达式转后缀表达式
     * 1、从左到右遍历
     * 2、数字则输出
     * 3、符号则判断：
     * 1、栈空入栈
     * 2、左括号入栈
     * 3、右括号，依次弹出栈符号并输出，直到左括号（不输出括号）
     * 4、加减乘除则弹出 [所有] 优先级 [大于或等于] 当前符号的元素，然后将当前符号入栈
     *
     * @return
     */
    private String infixTranPostfix(String exp) {
        //输出
        StringBuilder result = new StringBuilder();
        //栈
        LinkedList<Character> list = new LinkedList();
        //加减乘除优先表
        Map<Character, Integer> map = new HashMap<>();
        map.put('+', 1);
        map.put('-', 1);
        map.put('*', 2);
        map.put('/', 2);
        map.put('(', 0);
        for (char s : exp.toCharArray()) {
            if (s >= '0' && s <= '9') {
                result.append(s);
            } else if (list.isEmpty() || s == '(') {
                list.add(s);
            } else if (s == ')') {
                while (!list.isEmpty() && list.peekLast() != '(') {
                    result.append(list.pollLast());
                }
                list.pollLast();
            } else {
                while (!list.isEmpty() && map.get(list.peekLast()) >= map.get(s)) {
                    result.append(list.pollLast());
                }
                list.add(s);
            }
        }
        while (!list.isEmpty()) {
            result.append(list.pollLast());
        }
        return result.toString();
    }

    /**
     * 计算后缀表达式
     * 1、从左到有遍历
     * 2、遇到数字进栈
     * 3、遇到符号弹出两位计算并再入栈
     *
     * @param exp
     * @return
     */
    private String calculationPostfix(String exp) {
        //栈
        LinkedList<String> list = new LinkedList();
        for (String s : exp.split("")) {
            if (Pattern.matches("[0-9]", s)) {
                list.add(s);
            } else {
                //注意计算顺序，先出栈排后
                int i = Integer.parseInt(list.pollLast());
                int j = Integer.parseInt(list.pollLast());
                int k = 0;
                switch (s) {
                    case "+":
                        k = j + i;
                        break;
                    case "-":
                        k = j - i;
                        break;
                    case "*":
                        k = j * i;
                        break;
                    case "/":
                        k = j / i;
                        break;
                    default:
                        break;
                }
                list.add(String.valueOf(k));
            }
        }
        return list.pollLast();
    }
}
