package com.shipsupply.service;

import com.shipsupply.dto.*;
import com.shipsupply.entity.Item;
import com.shipsupply.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //모든 아이템 리스트 출력
    public List<ItemListDto> getItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(item -> new ItemListDto(item.getId(), item.getItem(), item.getMachinery(), item.getAssembly(),
                        item.getPartNo1(), item.getCategory(), item.getCompany(), item.getCurrency(),
                        item.getPrice(), item.getShip(), item.getSubject()))
                .collect(Collectors.toList());
    }

    // 검색창에서 카테고리만 출력
    public List<CategoryDto> getCategoriesByKeyword(String category) {
        return itemRepository.findByCategoryLike(category);
    }

    // 검색창에서 발주처만 출력
    public List<CompanyDto> getCompaniesByKeyword(String company) {
        return itemRepository.findByCompanyLike(company);
    }

    // 검색창에서 부품명만 출력
    public List<ItemDto> getItemByKeyword(String item) {
        return itemRepository.findByItemLike(item);
    }

    // 특정 카테고리를 포함하는 모든 행 출력
    public List<ItemListDto> findByCategory(String category) {
        List<Item> byCategoryContaining = itemRepository.findByCategoryContaining(category);

        return byCategoryContaining.stream()
                .map(item -> new ItemListDto(item.getId(), item.getItem(), item.getMachinery(), item.getAssembly(),
                        item.getPartNo1(), item.getCategory(), item.getCompany(), item.getCurrency(),
                        item.getPrice(), item.getShip(), item.getSubject()))
                .collect(Collectors.toList());
    }

    // 특정 부품명 포함하는 모든 행 출력
    public List<ItemListDto> findByItem(String item) {
        List<Item> byItemContaining = itemRepository.findByItemContaining(item);

        return byItemContaining.stream()
                .map(items -> new ItemListDto(items.getId(), items.getItem(), items.getMachinery(), items.getAssembly(),
                        items.getPartNo1(), items.getCategory(), items.getCompany(), items.getCurrency(),
                        items.getPrice(), items.getShip(), items.getSubject()))
                .collect(Collectors.toList());
    }

    // 특정 발주처 포함하는 모든 행 출력
    public List<ItemListDto> findByCompany(String company) {
        List<Item> byCompanyContaining = itemRepository.findByCompanyContaining(company);

        return byCompanyContaining.stream()
                .map(item -> new ItemListDto(item.getId(), item.getItem(), item.getMachinery(), item.getAssembly(),
                        item.getPartNo1(), item.getCategory(), item.getCompany(), item.getCurrency(),
                        item.getPrice(), item.getShip(), item.getSubject()))
                .collect(Collectors.toList());
    }

    // 과거 리드타임 추이
    public List<LeadtimeDto> getPastLeadtime(String item, String category, String company) {
        List<LeadtimeDto> getList = itemRepository.findByItemAndCategoryAndCompanyOrderByDate(item, category, company);
        log.info("getList : " + getList);
        return getList;
    }

    // 리드타임 분포
    public List<LeadtimeDistributionDto> getLeadtimeDistribution() {
        return itemRepository.findByLeadtimeAndCount();
    }

    // 카테고리 분포
    public List<CategoryDistributionDto> getCategoryDistribution() {
        return itemRepository.findByCategoryAndCountOrderByCategoryCountDesc();
    }

    // 카테고리 예측
    public Mono<String> predCategory(Map<String, String> data) {

        WebClient webClient = WebClient.create();

        String flaskUrl = "http://43.200.163.145:5000/api/item/predict/classify";

        Map<String, String> map = new HashMap<>();
        map.put("a", data.get("Machinery"));
        map.put("b", data.get("Assembly"));
        map.put("c", data.get("Company"));
        map.put("d", data.get("PartNo1"));
        map.put("e", data.get("PartNo2"));
        map.put("f", data.get("Item"));

        return webClient.post()
                .uri(flaskUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> predLeadtime(Map<String, String> data) {

//        String flaskUrl = "http://43.200.163.145:5000/api/item/predict/regression";
        String flaskUrl = "http://localhost:5000/api/item/predict/regression";

        WebClient webClient = WebClient.create();

        Map<String, String> map = new HashMap<>();
        map.put("a", data.get("subject"));
        map.put("b", data.get("ship"));
        map.put("c", data.get("key2"));
        map.put("d", data.get("assembly"));
        map.put("e", data.get("currency"));
        map.put("f", data.get("company"));

        return webClient.post()
                .uri(flaskUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class);
    }

}
