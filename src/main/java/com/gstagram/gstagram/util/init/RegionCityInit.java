package com.gstagram.gstagram.util.api;

import com.gstagram.gstagram.city.domain.City;
import com.gstagram.gstagram.city.repository.CityRepository;
import com.gstagram.gstagram.region.domain.Region;
import com.gstagram.gstagram.region.repository.RegionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.gstagram.gstagram.util.api.UrlJsonHandler.readJsonFromUrl;

/**
 * region, city에 대한 정보를 받아오는 class
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegionCityInit {


    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;


    /**
     * region, city에 대한 정보를 api를 통해 받아오는 method
     * PostConstruct 어노테이션을 붙여서 실제 spring boot에서 bean으로 다 등록되고 난 뒤
     * api로 받아온 정보를 처리하여 DB에 저장한다
     * 그냥 app 실행할 때 한번 실행된다고 생각하면 된다
     */
    @EventListener(ApplicationReadyEvent.class)
    public void RegionInit() throws IOException {

        // db에 city, region 정보 있으면 처리x
        if (regionRepository.count() > 0) {
            log.info("이미 DB에 저장 돼 있으므로 호출 종료");
            return;
        }

        //region을 db에 저장
        String url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00000000";
        JSONObject json = readJsonFromUrl(url);
        JSONArray regcodes = json.getJSONArray("regcodes");
        jsonToDB(regcodes);




        //city를 db에 저장
        regionRepository.findAll().forEach(region -> {
            String regionIdStr = region.getId().toString();
            String cityUrl = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=" + regionIdStr + "*000000";
            JSONObject cityJson;
            try {
                cityJson = readJsonFromUrl(cityUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //log.info(regionIdStr);
            //log.info(cityJson.toString());
            //log.info("-----------------------------");
            if (isNotEmpty(cityJson.toString())) {
                JSONArray regcodes1 = cityJson.getJSONArray("regcodes");
                jsonToDB(regcodes1);

            }


        });

    }

    private boolean isNotEmpty(String json) {
        return !json.isEmpty();

    }

    private void jsonToDB(JSONArray regcodes) {
        for (int i = 0; i < regcodes.length(); i++) {
            jsonToDB(regcodes.getJSONObject(i));
        }
    }

    // region과 관련된 json값을 처리하는 로직
    private void jsonToDB(JSONObject regcode) {

        String c = regcode.getString("code").substring(0, 4);
        if (c.endsWith("00")) {
            c = c.substring(0, 2);
        }
        Long code = Long.parseLong(c);
        //log.info(code.toString());
        String name = regcode.getString("name");
        //region을 db에 저장
        // repository에 없다면 지역 정보 넣어주기
        if (c.length() == 2 && regionRepository.findById(code).isEmpty()) {
            Region region = Region.builder().id(code).regionName(name).build();
            regionRepository.save(region);

        }

        //region을 db에 저장
        // repository에 없다면 지역 정보 넣어주기
        if (c.length() == 4 && cityRepository.findById(code).isEmpty()) {
            Region region = regionRepository.findById(Long.parseLong(c.substring(0, 2))).get();
            City build = City.builder().id(code).cityName(name).region(region).build();
            cityRepository.save(build);


        }


    }

}
