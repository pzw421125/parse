package cn.pa.jsoup;

import cn.pa.jsoup.PoJo.Option;
import cn.pa.jsoup.PoJo.Question;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void parse(){
        String s = "2.下列各项中，除哪项外均是冠心病的西医分型\n" +
                "\n" +
                "　　A.隐匿型\n" +
                "\n" +
                "　　B.心绞痛型\n" +
                "\n" +
                "　　C.猝死型\n" +
                "\n" +
                "　　D.继发性心脏骤停型\n" +
                "\n" +
                "　　E.心肌梗死型\n" +
                "\n" +
                "　　【参考答案】D\n\n" +
                "\n" +
                "3.胸痹的总病机是\n" +
                "\n" +
                "　　A.气血失和\n" +
                "\n" +
                "　　B.寒热错杂\n" +
                "\n" +
                "　　C.气血两虚\n" +
                "\n" +
                "　　D.本虚标实\n" +
                "\n" +
                "　　E.上盛下虚\n" +
                "\n" +
                "　　【参考答案】D\n";
        String s2 = "A.隐匿型\n" +
                    "\n";
        s=s.replaceAll("\\\\s*|\\t|\\r","");
        System.out.println("字符串内容"+s);
        //通过答案来分割题目，变成数组
        String questionRegx = "【参考答案】([A-G]{1})";
        Pattern pattern = Pattern.compile(questionRegx);
        String[] strArr=s.split(questionRegx);
        List<String> list = Arrays.asList(strArr);
        List<String> arrayList=new ArrayList<String>(list);
        //分割的最后一个是没有内容的,去掉
        arrayList.remove(arrayList.size()-1);
        //选项和题干列表
        System.out.println("题目列表："+arrayList.toString());

        Matcher ANmatcher = pattern.matcher(s);
        Map<Integer,String> answerMap = new HashMap<>();
        int i=0;
        while(ANmatcher.find()){
            String answer = ANmatcher.group();
            answer= answer.trim().replaceAll("【参考答案】","");
            answerMap.put(i++,answer);
        }

        System.out.println(answerMap.toString());
        List<Question> questionList = new ArrayList<>();
        for (int j = 0; j <arrayList.size() ; j++) {
            String question = arrayList.get(j).trim();
            System.out.println(question);
            //获取考试Id。
            //编译正则表达式
            Pattern idPattern = Pattern.compile("[\\d]+");
            String questionId = question.substring(0,4);
            Matcher idMatcher = idPattern.matcher(questionId);
            //截取题目ID
            Integer id=0;
            while (idMatcher.find()){
                 id = Integer.parseInt(idMatcher.group());
            }
            //Id放入题目实体
            Question questionEntity = new Question();
            questionEntity.setQuestionId(id);
            //题目内容放入题目实体
            Pattern contentPattern = Pattern.compile("(?<=^[\\d+]\\.).*(?=\\n)");
            Matcher contentMatcher = contentPattern.matcher(question);
            String item="";
            while (contentMatcher.find()){
                item = contentMatcher.group();
            }
            questionEntity.setQuestionItem(item);
            //^
            ArrayList<Option> optionList = new ArrayList<>();
            Pattern patten = Pattern.compile("([A-G]\\.)+.+[\\n\\s]");
            Matcher OPmatcher = patten.matcher(question);// 指定要匹配的字符串

            while (OPmatcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
               String optionStr = OPmatcher.group();
               Option option = new Option();
               //设置选项
                String abcd = optionStr.trim().substring(0,1);
               option.setOption(abcd);
               if(answerMap.get(j).contains(abcd)){
                   option.setOptionFlag(true);
               }else {
                   option.setOptionFlag(false);
               }
               option.setOptionContent(optionStr.trim().substring(2));
               optionList.add(option);
            }
            //设置选项
            questionEntity.setOptions(optionList);
            System.out.println(questionEntity.toString());
            //设置答案数量
            questionEntity.setOptionNum(answerMap.get(j).length());
            if(questionEntity.getOptionNum()>1){
                questionEntity.setQuestionType("m");
            }else{
                questionEntity.setQuestionType("s");
            }
            questionList.add(questionEntity);
        }

        System.out.println(questionList.toString());


    }
    @Test
    public void t1(){
        String s = "1、1、1、1、1、1、1、1、1、";
        String regex = "(\\d{1,3}\\、)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(s);

        ArrayList<String> answerlist = new ArrayList<>();
        while(matcher.find()){
            answerlist.add(matcher.group().replaceAll("(\\d{1,3}\\、)",""));
            System.out.println("----"+matcher.group()+"------");
        }
        System.out.println(answerlist.toString());
        System.out.println(answerlist.size());
    }

    @Test
    public void trim(){
        String s = "18、【正确答案】A\n" +
                "\n" +
                "【答案解析】大黄功效：泻下攻积，清热泻火，凉血解毒，逐瘀通经。用于(1)积滞便秘;(2)血热吐衄、目赤咽肿;(3)热毒疮疡，烧烫伤;(4)瘀血诸证;(5)湿热痢疾、黄疸、淋证。\n" +
                "\n" +
                "19、【正确答案】B   \n" +
                "\n" +
                "\n" +
                "20、【正确答案】A\n" +
                "\n" +
                "【答案解析】大黄  用于治疗积滞便秘，\n" +
                "有较强泻下作用，能荡涤胃肠，推陈致新，\n" +
                "为治疗积滞便秘之要药。又因其苦寒沉降，善泄热，故实热便秘尤为适宜。";
        s=s.replaceAll("【正确答案】[A-K]","");
        s = s.replaceAll("[\n\\s]","");
        s=s.replaceAll("【答案解析】","");
        System.out.println(s);
        Pattern parsePattern = Pattern.compile("(\\d{1,3}[\\.\\、])");
        Matcher parseMatcher = parsePattern.matcher(s);
        String questionRegx = "(\\d{1,3}[\\.\\、])";
        Pattern pattern = Pattern.compile(questionRegx);
        List<Integer> questionIdlist = new ArrayList<>();
        while(parseMatcher.find()){
            String questionId =  parseMatcher.group().replaceAll("[\\、\\.]","");
            questionIdlist.add(Integer.parseInt(questionId));
        }
        System.out.println(questionIdlist.toString());
        String[] strArr=s.split(questionRegx);
        List<String> list = Arrays.asList(strArr);
        List<String> parseList=new ArrayList<String>(list);
        parseList.remove(0);
        System.out.println(parseList);
        Map<Integer,String> parseMap = new HashMap<>();
        if(questionIdlist.size()==parseList.size()){
            for (int i = 0; i <questionIdlist.size() ; i++) {
                parseMap.put(questionIdlist.get(i),parseList.get(i));
            }
        }else {
            System.out.println("解析题号和内容对应不上");
        }
        System.out.println(parseMap.toString());
    }

}
