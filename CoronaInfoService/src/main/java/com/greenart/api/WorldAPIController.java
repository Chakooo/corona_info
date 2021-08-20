package com.greenart.api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.greenart.service.WorldInfoService;
import com.greenart.vo.CoronaWorldInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
public class WorldAPIController {
    @Autowired
    WorldInfoService w_service;
    
    @GetMapping("/api/coronaWorld")
    public Map<String, Object> getCoronaWorldInformation(
        @RequestParam String startDt, 
        @RequestParam String endDt
        )throws Exception{
        // 날짠 send 형식 : 20201023 
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2FWTxzUx7RRv4Y4NgmmONDy5QfMajED80WjbrFMT%2BcPb29GeWTTfxq8dR%2FVneVoJTF8vUpbtDEaS4y1d9DURapQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDt, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDt, "UTF-8")); /*검색할 생성일 범위의 종료*/

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
           Date dt = formatter.parse(createDt);

            CoronaWorldInfoVO vo = new CoronaWorldInfoVO();
            vo.setAreaNm(areaNm);
            vo.setCreateDt(dt);
            vo.setNatDeathCnt(natDeathCnt);
            vo.setNatDeathRate(DR);
            vo.setNatDefCnt(natDefCnt);
            vo.setNationNm(nationNm);
            vo.setNationNmEn(nationNmEn);
            w_service.insertCoronaWorldInfo(vo);
        }        
        return resultMap;
    }
    @GetMapping("/api/corona/world/{date}")
    public Map<String,Object> getCoronaWorldInfo(@PathVariable String date){
        Map<String, Object> resultMap = new LinkedHashMap<String,Object>();

        

        CoronaWorldInfoVO vo= w_service.selectWorldCoronaSum(date);
        resultMap.put("data", vo);
        return resultMap;
    }

    @GetMapping("/api/corona/chart/{term}/{date}")
    public Map<String,Object> getCoronaWorldChart(@PathVariable String date ,@PathVariable @Nullable String term){
        Map<String, Object> resultMap = new LinkedHashMap<String,Object>();
        
        w_service.selectChartInfo(date, term);        
        return resultMap;
    }













    public static String getTagValue(String tag, Element elem){
        NodeList nlList = elem.getElementsByTagName(tag).item(0).getChildNodes();
        if(nlList == null) return null;
        Node node = (Node) nlList.item(0);
        if(node == null) return null;
        return node.getNodeValue();
    }

    
}
