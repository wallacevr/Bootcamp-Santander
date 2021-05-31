package com.project.bootcamp.santander.service;

import com.project.bootcamp.santander.Exceptions.BusinessException;
import com.project.bootcamp.santander.Exceptions.NotFoundException;
import com.project.bootcamp.santander.mapper.StockMapper;
import com.project.bootcamp.santander.model.Stock;
import com.project.bootcamp.santander.model.dto.StockDTO;
import com.project.bootcamp.santander.repository.StockRepository;
import com.project.bootcamp.santander.util.MessageUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalStock =  repository.findByNameAndDate(dto.getName(),dto.getDate());
        if(optionalStock.isPresent()){
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }
        Stock stock = mapper.toEntity(dto);
            repository.save(stock);
            dto.setId(stock.getId());
            return mapper.toDto(stock);
    }
    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optionalStock =  repository.findByStockUpdate(dto.getName(),dto.getDate(),dto.getId());
        if(optionalStock.isPresent()){
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
        }
        Stock stock = mapper.toEntity(dto);
        repository.save(stock);

        return mapper.toDto(stock);
    }

    @Transactional
    public List<StockDTO> findAll() {

        return mapper.toDto(repository.findAll());
    }
    @Transactional
    public StockDTO findById(Long id) {
        return  repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }
    @Transactional
    public StockDTO delete(long id) {
        StockDTO dto = this.findById(id);
        repository.deleteById(id);
        return  dto;
    }
    @Transactional
    public List<StockDTO> findByToday() {
        return  repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }
}
