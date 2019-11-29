package cn.pa.jsoup.Utils;

import cn.pa.jsoup.PoJo.Option;
import cn.pa.jsoup.PoJo.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexWordUtils {
    private static final Logger logger= LoggerFactory.getLogger(regexWordUtils.class);
    public static List<Question> toQuestion(String s,String answerRegex){
        s=s.replaceAll("\\\\s*|\\t|\\r","");
        //System.out.println("字符串内容"+s);
        //通过答案来分割题目，变成数组
        String questionRegx = answerRegex+"([A-G]{1})";
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
            answer= answer.trim().replaceAll(answerRegex,"");
            answerMap.put(i++,answer);
        }

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
            Pattern contentPattern = Pattern.compile("(?<=^[\\d]{1,3}\\、).*(?=\\n)");
            Matcher contentMatcher = contentPattern.matcher(question);
            String item="";
            while (contentMatcher.find()){
                item = contentMatcher.group();
            }
            questionEntity.setQuestionItem(item);
            //^
            ArrayList<Option> optionList = new ArrayList<>();
            ArrayList<Option> answerList = new ArrayList<>();
            Pattern patten = Pattern.compile("([A-G](\\.|\\、))+.+[\\n\\s]");
            Matcher OPmatcher = patten.matcher(question);// 指定要匹配的字符串

            while (OPmatcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                String optionStr = OPmatcher.group();
                Option option = new Option();
                //设置选项
                String abcd = optionStr.trim().substring(0,1);
                option.setOption(abcd);
                option.setOptionContent(optionStr.trim().substring(2));
                if(answerMap.get(j).contains(abcd)){
                    option.setOptionFlag(true);
                    answerList.add(option);
                }else {
                    option.setOptionFlag(false);
                }
                optionList.add(option);
            }
            //设置选项
            questionEntity.setOptions(optionList);
            //设置答案
            questionEntity.setAnswerOptions(answerList);
            System.out.println(questionEntity.toString());
            //设置答案数量
            questionEntity.setOptionNum(optionList.size());
            if(questionEntity.getAnswerOptions().size()>1){
                questionEntity.setQuestionType("m");
            }else{
                questionEntity.setQuestionType("s");
            }
            questionList.add(questionEntity);
        }

        return questionList;

    }


    public static List<Question> SeptoQuestion(String s,String answerRegex){
        s=s.replaceAll("\\\\s*|\\t|\\r","");
        //获取题干列表
        Map<Integer,String> questionItemMap = getQuestionMap(s);
        List<Question> questionList = new ArrayList<>();
        //处理题目选项
        Pattern optionPattern = Pattern.compile("(?<=\\n)(([A-G](\\.|\\、))+.*+[\\n]{1,3}){1,10}");
        Matcher optionMatcher = optionPattern.matcher(s);
        List<String> optionList = new ArrayList<>();
        while (optionMatcher.find()){
            optionList.add(optionMatcher.group());
        }
        System.out.println("选项长度"+optionList.size());
        System.out.println(optionList.toString());
        //处理答案和解析
        //获取全部
        Pattern answerPattern = Pattern.compile("(\\d{1,3}[\\.\\、])【正确答案】[A-K][\\w\\W]*");
        Matcher answerMatcher = answerPattern.matcher(s);
        String answerAndParseStr = "";
        while(answerMatcher.find()){
            answerAndParseStr += answerMatcher.group();
        }
        System.out.println();

        //获取答案
        Map<Integer,String> answerMap = getAnswerMap(answerAndParseStr);
        Map<Integer,String> parseMap = getParseMap(answerAndParseStr);
        questionList = getQuestion(optionList,answerMap,parseMap,questionItemMap);
        return questionList;
    }

    public static List<Question> getQuestion(List<String> questionList,Map<Integer,String> answerMap,
                                             Map<Integer,String> parseMap,Map<Integer,String> questionItemMap ){

        Pattern patten = Pattern.compile("([A-G](\\.|\\、))+.+[\\n\\s]");
        String question = "";

        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <=questionList.size() ; i++) {
            Question questionEntity = new Question();
            ArrayList<Option> optionList = new ArrayList<>();
            ArrayList<Option> answerList = new ArrayList<>();
            question = questionList.get(i-1);
            Matcher OPmatcher = patten.matcher(question);// 指定要匹配的字符串

            while (OPmatcher.find()) { //此处find（）每次被调用后，会偏移到下一个匹配
                String optionStr = OPmatcher.group();
                Option option = new Option();
                //设置选项
                String abcd = optionStr.trim().substring(0,1);

                option.setOption(abcd);
                option.setOptionContent(optionStr.trim().substring(2));

                try {
                    if(answerMap.get(i).contains(abcd)){
                        option.setOptionFlag(true);
                        answerList.add(option);
                    }else {
                        option.setOptionFlag(false);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                optionList.add(option);
            }
            //设置选项
            questionEntity.setOptions(optionList);
            questionEntity.setQuestionItem(questionItemMap.get(i));
            //设置答案
            questionEntity.setAnswerOptions(answerList);
            questionEntity.setOptionNum(optionList.size());
            questionEntity.setQuestionId(i);
            questionEntity.setQuestionDesc(parseMap.get(i));
            if(questionEntity.getOptionNum()>1){
                questionEntity.setQuestionType("s");
            }else {
                questionEntity.setQuestionType("m");
            }
            questions.add(questionEntity);
        }
        System.out.println(questions.toString());
        return questions;
    }

    //获取题干的集合
    public static Map<Integer,String> getQuestionMap(String s){
        Map<Integer,String> questionMap = new HashMap<>();
        Pattern questionPattern = Pattern.compile("(\\d{1,3})\\、([^【]).*");
        Matcher questionMatcher = questionPattern.matcher(s);
        //处理题干
        List<String> questionItemList = new ArrayList<>();
        while (questionMatcher.find()){
            questionItemList.add(questionMatcher.group());
        }
        System.out.println(questionItemList);
        Integer questionItemId =0;
        String questionItem ="";
        String questionItemStr= "";
        for (int i = 0; i <questionItemList.size() ; i++) {
            questionItemStr = questionItemList.get(i);
            questionItemId = Integer.parseInt(questionItemStr.substring(0,questionItemStr.indexOf("、"))) ;
            questionItem = questionItemStr.substring(questionItemStr.indexOf("、")+1);
            questionMap.put(questionItemId,questionItem);
        }
        System.out.println(questionMap);
        return  questionMap;
    }
    //获取答案的集合
    public static  Map<Integer,String> getAnswerMap(String s){
        Map<Integer,String> answerMap = new HashMap<>();
        Pattern answerPattern = Pattern.compile("(\\d{1,3}[\\.\\、])【正确答案】[A-K]");
        Matcher answerMatcher = answerPattern.matcher(s);
        List<String> answerList = new ArrayList<>();
        while (answerMatcher.find()){
            answerList.add(answerMatcher.group());
        }
        Integer answerId = 0;
        String answer = "";
        String answerStr = "";
        System.out.println(answerList.toString());
        for (int i = 0; i <answerList.size() ; i++) {
            answerStr = answerList.get(i);
            answerId =  Integer.parseInt(answerStr.substring(0,answerStr.indexOf("【")-1));
            answer = answerStr.substring(answerStr.lastIndexOf("】")+1);
            answerMap.put(answerId,answer);
        }
        System.out.println(answerMap);
        return  answerMap;
    }
    //获取解析的map集合
    public static Map<Integer,String> getParseMap(String s){
        Boolean haveParseFlag = false;
        if(s.contains("【答案解析】")){
            haveParseFlag = true;
        }
        s=s.replaceAll("【正确答案】[A-K]","");
        s = s.replaceAll("[\n\\s]","");
        s=s.replaceAll("【答案解析】","");
        System.out.println(s);
        Pattern parsePattern = Pattern.compile("(\\d{1,3}\\、)");
        Matcher parseMatcher = parsePattern.matcher(s);
        String questionRegx = "(\\d{1,3}\\、)";
        Pattern pattern = Pattern.compile(questionRegx);
        List<Integer> questionIdlist = new ArrayList<>();
        while(parseMatcher.find()){
            String questionId =  parseMatcher.group().replaceAll("[\\、\\.]","");
            questionIdlist.add(Integer.parseInt(questionId));
        }
        System.out.println(questionIdlist.toString());

        List<String> parseList;
        if(haveParseFlag==true){
            String[] strArr=s.split(questionRegx);
            List<String> list = Arrays.asList(strArr);
            parseList=new ArrayList<String>(list);
            parseList.remove(0);
        }else {
            parseList=new ArrayList<>();
            parsePattern = Pattern.compile("(\\d{1,3}[\\、\\.])");
            parseMatcher = parsePattern.matcher(s);
            while (parseMatcher.find()){
                parseList.add(parseMatcher.group().replaceAll("\\d{1,3}[\\、]",""));
            }
        }
        System.out.println(parseList);
        System.out.println("解析的长度"+parseList.size());
        Map<Integer,String> parseMap = new HashMap<>();
        if(questionIdlist.size()==parseList.size()){
            for (int i = 0; i <questionIdlist.size() ; i++) {
                parseMap.put(questionIdlist.get(i),parseList.get(i));
            }
        }else {
            System.out.println("解析题号和内容对应不上");
        }
        System.out.println(parseMap.toString());
        return parseMap;
    }


    public static void main(String[] args) {
        String answerRegx = "【正确答案】";
        String s = "1、足阳明胃经的起始穴位是()\n" +
                "\n" +
                "A、大包\n" +
                "B、睛明\n" +
                "C、承泣\n" +
                "D、四白\n" +
                "E、厉兑\n" +
                "\n" +
                "2、首先提出“泻者迎之，补者随之”的是()\n" +
                "\n" +
                "A、《素问》\n" +
                "B、《金针赋》\n" +
                "C、《灵枢》\n" +
                "D、《针经指南》\n" +
                "E、《千金要方》\n" +
                "\n" +
                "3、在胸部，任脉旁开2寸的经脉是()\n" +
                "\n" +
                "A、足太阴脾经\n" +
                "B、手太阴肺经\n" +
                "C、足阳明胃经\n" +
                "D、足少阳胆经\n" +
                "E、足少阴肾经\n" +
                "\n" +
                "4、治疗胆绞痛伴恶心呕吐者，可在基础方上加用()\n" +
                "\n" +
                "A、支沟、外关\n" +
                "B、三阴交、阴陵泉\n" +
                "C、百虫窝、迎香\n" +
                "D、内关、足三里\n" +
                "E、至阳、肝俞\n" +
                "\n" +
                "5、按八会穴主治，瘀血证宜取()\n" +
                "\n" +
                "A、气海\n" +
                "B、血海\n" +
                "C、膈俞\n" +
                "D、太渊\n" +
                "E、都不对\n" +
                "\n" +
                "6、直接入耳中的经脉有()\n" +
                "\n" +
                "A、手太阳小肠经、手少阳三焦经、足少阳胆经\n" +
                "\n" +
                "\n" +
                "B、任脉、督脉\n" +
                "\n" +
                "\n" +
                "C、手太阴肺经、手厥阴心包经、足少阴肾经\n" +
                "\n" +
                "\n" +
                "D、手太阳小肠经、手少阳三焦经、足少阳胆经、足阳明胃经\n" +
                "\n" +
                "\n" +
                "E、足阳明胃经、足少阳胆经\n" +
                "\n" +
                "7、取养老穴宜用的揣穴法是()\n" +
                "\n" +
                "A、指切\n" +
                "B、旋转\n" +
                "C、分拨\n" +
                "D、滚摇\n" +
                "E、按压\n" +
                "\n" +
                "8、三叉神经痛(第一支)宜取穴()\n" +
                "\n" +
                "A、足三里、合谷\n" +
                "B、攒竹、阳白\n" +
                "C、四白、颧髎\n" +
                "D、夹承浆、下关\n" +
                "E、青灵、小海\n" +
                "\n" +
                "9、胆经上有募穴之称的为()\n" +
                "\n" +
                "A、头窍阴\n" +
                "B、日月\n" +
                "C、章门\n" +
                "D、肩井\n" +
                "E、风市\n" +
                "\n" +
                "10、小肠的募穴是()\n" +
                "\n" +
                "A、中极\n" +
                "B、关元\n" +
                "C、气海\n" +
                "D、神阙\n" +
                "E、中脘\n" +
                "\n" +
                "11、治疗六腑痛症宜选()\n" +
                "\n" +
                "A、井穴\n" +
                "B、原穴\n" +
                "C、络穴\n" +
                "D、俞穴\n" +
                "E、募穴\n" +
                "\n" +
                "12、治疗阴道流血过多宜取()\n" +
                "\n" +
                "A、大都\n" +
                "B、隐白\n" +
                "C、商丘\n" +
                "D、至阴\n" +
                "E、大包\n" +
                "\n" +
                "13、伏兔穴位于()\n" +
                "\n" +
                "A、髂前上棘与髌底外侧端的连线上，髌底上7寸\n" +
                "\n" +
                "\n" +
                "B、髂前上棘与髌底内侧端的连线上，髌底上7寸\n" +
                "\n" +
                "\n" +
                "C、髂前上棘与髌底外侧端的连线上，髌底上6寸\n" +
                "\n" +
                "\n" +
                "D、髂前上棘与髌底外侧端的连线上，髌底上8寸\n" +
                "\n" +
                "\n" +
                "E、髂嵴高点与髌底外侧端的连线上，髌底上4寸\n" +
                "\n" +
                "14、“轻滑慢而未来，沉涩紧而已至”，语出()\n" +
                "\n" +
                "A、《灵枢·九针十二原》\n" +
                "B、《标幽赋》\n" +
                "C、《金针赋》\n" +
                "D、《千金方》\n" +
                "E、《针灸甲乙经》\n" +
                "\n" +
                "15、位于小指末节桡侧，指甲角旁0、1寸处的穴位是()\n" +
                "\n" +
                "A、少海\n" +
                "B、小海\n" +
                "C、少泽\n" +
                "D、少冲\n" +
                "E、中冲\n" +
                "\n" +
                "\n" +
                "1、【正确答案】C\n" +
                "2、【正确答案】C\n" +
                "3、【正确答案】E\n" +
                "4、【正确答案】D\n" +
                "5、【正确答案】C\n" +
                "6、【正确答案】A\n" +
                "7、【正确答案】B\n" +
                "8、【正确答案】B\n" +
                "9、【正确答案】B\n" +
                "10、【正确答案】B\n" +
                "11、【正确答案】E\n" +
                "12、【正确答案】B\n" +
                "13、【正确答案】C\n" +
                "14、【正确答案】B\n" +
                "15、【正确答案】D\n";
        List<Question>  questionList ;
        if(s.contains(answerRegx)){
            questionList = SeptoQuestion(s,answerRegx);
        }else {
            questionList = null;
        }
//        System.out.println(questionList.size());
//        for (int i = 0; i <questionList.size() ; i++) {
//            System.out.println(questionList.get(i).toString());
//        }
    }
}
