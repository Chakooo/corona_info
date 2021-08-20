package com.greenart.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.greenart.service.CoronaInfoService;
import com.greenart.service.WorldInfoService;
import com.greenart.vo.CoronaAgeInfoVO;
import com.greenart.vo.CoronaInfoVO;
import com.greenart.vo.CoronaSidoInfoVO;
import com.greenart.vo.CoronaVaccineInfoVO;
import com.greenart.vo.CoronaWorldInfoVO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class CoronaInfoComponent {
    // 매일 10시 30분에 한 번 호출
    @Autowired
    CoronaInfoService service;
    @Autowired
    WorldInfoService w_service;

    @Scheduled(cron = "0 30 10 * * *")
    public void getCoronaInfo() throws Exception {
        System.out.println("cron schedule");
        Date dt = new Date(); // 현재시간
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyyMMdd");
        String today = dtFormatter.format(dt);

        StringBuilder urlBuilder = new StringBuilder(
                "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /* URL */
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
                + "=Qvh%2FPxBBmg3Pp64QitOr7PScIkH25vOjdehJK4Fr4N2ITDAoFZl7TONz6l%2Bovat%2BrMpoRgfFwWIXMssHOkAmVw%3D%3D"); /*
                                                                                                                           * Service
                                                                                                                           * Key
                                                                                                                           */
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
                + URLEncoder.encode("-", "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        urlBuilder
                .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
                + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 시작 */
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 종료 */

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
        if (nList.getLength() <= 0) {
            return;
        }
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element elem = (Element) node;
            CoronaInfoVO vo = new CoronaInfoVO();
            vo.setAccExamCnt(Integer.parseInt(getTagValue("accExamCnt", elem)));
            vo.setAccExamCompCnt(Integer.parseInt(getTagValue("accExamCompCnt", elem)));
            vo.setCareCnt(Integer.parseInt(getTagValue("careCnt", elem)));
            vo.setClearCnt(Integer.parseInt(getTagValue("clearCnt", elem)));
            vo.setDeathCnt(Integer.parseInt(getTagValue("deathCnt", elem)));
            vo.setDecideCnt(Integer.parseInt(getTagValue("decideCnt", elem)));
            vo.setExamCnt(Integer.parseInt(getTagValue("examCnt", elem)));
            vo.setResultNegCnt(Integer.parseInt(getTagValue("resutlNegCnt", elem)));
            // String to Date
            Date createdt = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createdt = dtFormat.parse(getTagValue("createDt", elem));
            vo.setStateTime(createdt);
            service.insertCoronaInfo(vo);
        }
    }

    @Scheduled(cron = "10 30 10 * * *")
    public void getCoronaSido() throws Exception {
        Date dt = new Date();
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyyMMdd");
        String today = dtFormatter.format(dt);
        StringBuilder urlBuilder = new StringBuilder(
                "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /* URL */
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
                + "=Qvh%2FPxBBmg3Pp64QitOr7PScIkH25vOjdehJK4Fr4N2ITDAoFZl7TONz6l%2Bovat%2BrMpoRgfFwWIXMssHOkAmVw%3D%3D"); /*
                                                                                                                           * Service
                                                                                                                           * Key
                                                                                                                           */
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
                + URLEncoder.encode("-", "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        urlBuilder
                .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
                + URLEncoder.encode("10000", "UTF-8")); /* 한 페이지 결과 수 */
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 시작 */
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 종료 */

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("item");
        if (nList.getLength() <= 0) {
            return;
        }
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element elem = (Element) node;
            CoronaSidoInfoVO vo = new CoronaSidoInfoVO();
            vo.setDeathCnt(Integer.parseInt(getTagValue("deathCnt", elem)));
            vo.setDefCnt(Integer.parseInt(getTagValue("defCnt", elem)));
            vo.setGubun(getTagValue("gubun", elem));
            vo.setIncDec(Integer.parseInt(getTagValue("incDec", elem)));
            vo.setIsolClearCnt(Integer.parseInt(getTagValue("isolClearCnt", elem)));
            vo.setIsolIngCnt(Integer.parseInt(getTagValue("isolIngCnt", elem)));
            vo.setLocalOccCnt(Integer.parseInt(getTagValue("localOccCnt", elem)));
            vo.setOverFlowCnt(Integer.parseInt(getTagValue("overFlowCnt", elem)));
            // String to Date
            Date createDt = new Date();
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createDt = dtFormat.parse(getTagValue("createDt", elem));
            vo.setCreateDt(createDt);
            service.insertCoronaSido(vo);
        }
    }

    public static String getTagValue(String tag, Element elem) {
        NodeList nlList = elem.getElementsByTagName(tag).item(0).getChildNodes();
        if (nlList == null)
            return null;
        Node node = (Node) nlList.item(0);
        if (node == null)
            return null;
        return node.getNodeValue();
    }

    // 매일 16:00:00 에 한번실행
    @Scheduled(cron = "00 00 16 * * *")
    public void getCoronaAgeInfo() throws Exception {

        Date dt = new Date(); // 현재시간
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyyMMdd");
        String today = dtFormatter.format(dt);
        System.out.println("오늘날짜 : " + today);

        StringBuilder urlBuilder = new StringBuilder(
                "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson"); /* URL */
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8")
                + "=Qvh%2FPxBBmg3Pp64QitOr7PScIkH25vOjdehJK4Fr4N2ITDAoFZl7TONz6l%2Bovat%2BrMpoRgfFwWIXMssHOkAmVw%3D%3D"); /*
                                                                                                                           * Service
                                                                                                                           * Key
                                                                                                                           */
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
                + URLEncoder.encode("-", "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
        urlBuilder
                .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
                + URLEncoder.encode("100000", "UTF-8")); /* 한 페이지 결과 수 */
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 시작 */
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="
                + URLEncoder.encode(today, "UTF-8")); /* 검색할 생성일 범위의 종료 */

        System.out.println(urlBuilder);
        System.out.println("ageInfo UrlBuilder 종료");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        // System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("item");
        System.out.println(nList.getLength());

        for (int i = 0; i < nList.getLength(); i++) {
            // Node n = nList.item(i);
            // Element elem = (Element)n;
            Element elem = (Element) nList.item(i);

            String confCase = getTagValue("confCase", elem);
            String confCaseRate = getTagValue("confCaseRate", elem);
            String createDt = getTagValue("createDt", elem);
            String criticalRate = getTagValue("criticalRate", elem);
            String death = getTagValue("death", elem);
            String deathRate = getTagValue("deathRate", elem);
            String gubun = getTagValue("gubun", elem);

            if (gubun.equals("0-9"))
                gubun = "0";
            else if (gubun.equals("10-19"))
                gubun = "10";
            else if (gubun.equals("20-29"))
                gubun = "20";
            else if (gubun.equals("30-39"))
                gubun = "30";
            else if (gubun.equals("40-49"))
                gubun = "40";
            else if (gubun.equals("50-59"))
                gubun = "50";
            else if (gubun.equals("60-69"))
                gubun = "60";
            else if (gubun.equals("70-79"))
                gubun = "70";
            else if (gubun.equals("80 이상"))
                gubun = "80";

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(createDt);

            CoronaAgeInfoVO vo = new CoronaAgeInfoVO();
            vo.setConfCase(Integer.parseInt(confCase));
            vo.setConfCaseRate(Double.parseDouble(confCaseRate));
            vo.setCreateDt(date);
            vo.setCriticalRate(Double.parseDouble(criticalRate));
            vo.setDeath(Integer.parseInt(death));
            vo.setDeathRate(Double.parseDouble(deathRate));
            vo.setGubun(gubun);
            // System.out.println(vo);

            service.insertCoronaAge(vo);
            System.out.println("코로나 나이/성별 DB로 입력완료");

        }
    }

    // 매일 10:00:00 에 실행
    @Scheduled(cron = "00 00 10 * * *")
    public void getCoronaVaccineInfo() throws Exception {
        Date dy = new Date(); // 현재시간
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String today = dtFormatter.format(dy)+ " 00:00:00";
        System.out.println("오늘날짜 : " + today);

        StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/15077756/v1/vaccine-stat"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=3CID6KRU4kjF4jvHanoFBLwycg6Htt86aVfgEOgBmAecshZIcO5EC9UM9FhVGwAX2Zf%2B%2FrxgsJeUfled1zNS0w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("page","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("perPage","UTF-8") + "=" + URLEncoder.encode("100000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("cond[baseDate::EQ]","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 시작*/
      
    
        // 날짜를 특정해주지않으면 전체데이터를 가져와진다.
        URL url = new URL(urlBuilder.toString());
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();


        String line;
        while((line = rd.readLine()) != null){
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // System.out.println(sb.toString());

        JSONObject jsonObject = new JSONObject(sb.toString());
        // JSONParser jsonParser = new JSONParser();        
        //pom.xml 을 수정한후에는 project에 적용시켜주기위해 프로젝트를 다시 돌린다.

        Integer cnt = jsonObject.getInt("currentCount");
        System.out.println("Count : "+ cnt);

        JSONArray dataArray = jsonObject.getJSONArray("data");

        for(int i = 0 ; i < dataArray.length();i++){
            JSONObject obj = dataArray.getJSONObject(i);
            Integer accumulatedFirstCnt = obj.getInt("accumulatedFirstCnt");
            Integer accumulatedSecondCnt = obj.getInt("accumulatedSecondCnt");
            String baseDate = obj.getString("baseDate");
            Integer firstCnt = obj.getInt("firstCnt");
            Integer secondCnt = obj.getInt("secondCnt");
            String sido = obj.getString("sido");


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = formatter.parse(baseDate);
            

            CoronaVaccineInfoVO vo = new CoronaVaccineInfoVO();
            vo.setAccFirstCnt(accumulatedFirstCnt);
            vo.setAccSecondCnt(accumulatedSecondCnt);
            vo.setRegDt(dt);
            vo.setFirstCnt(firstCnt);
            vo.setSecondCnt(secondCnt);
            vo.setSido(sido);

            service.insertCoronaVaccineInfo(vo);
        }
    }

    @Scheduled(cron = "00 00 13 * * *")
    public void getCoronaWorldInformation()throws Exception{
        // 날짠 send 형식 : 20201023 

        Date dt = new Date();
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyyMMdd");
        String today = dtFormatter.format(dt);
        System.out.println(dt);

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2FWTxzUx7RRv4Y4NgmmONDy5QfMajED80WjbrFMT%2BcPb29GeWTTfxq8dR%2FVneVoJTF8vUpbtDEaS4y1d9DURapQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*검색할 생성일 범위의 종료*/

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(urlBuilder.toString());

        doc.getDocumentElement().normalize();
        // System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("item");
        System.out.println(nList.getLength());
        
        for(int i=0 ; i< nList.getLength();i++){
            // Node n = nList.item(i);
            // Element elem = (Element)n;
            Element elem = (Element)nList.item(i);

            String natDeathRate =  getTagValue("natDeathRate", elem);

            if(natDeathRate.equals("NaN")){
                natDeathRate = "0.0";                                
            }
            System.out.println();


           String areaNm = getTagValue("areaNm", elem);
           String createDt = getTagValue("createDt", elem);
           Integer natDeathCnt =Integer.parseInt(getTagValue("natDeathCnt", elem)) ;
           Double DR = Double.parseDouble(natDeathRate) ;
           Integer natDefCnt = Integer.parseInt(getTagValue("natDefCnt", elem)) ;
           String nationNm = getTagValue("nationNm", elem);
           String nationNmEn = getTagValue("nationNmEn", elem);

           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date create_dt= formatter.parse(createDt);

            CoronaWorldInfoVO vo = new CoronaWorldInfoVO();
            vo.setAreaNm(areaNm);
            vo.setCreateDt(create_dt);
            vo.setNatDeathCnt(natDeathCnt);
            vo.setNatDeathRate(DR);
            vo.setNatDefCnt(natDefCnt);
            vo.setNationNm(nationNm);
            vo.setNationNmEn(nationNmEn);
            w_service.insertCoronaWorldInfo(vo);
           System.out.println(create_dt);
        }        
  
        System.out.println("월드인포 인서트");
    }
   




}