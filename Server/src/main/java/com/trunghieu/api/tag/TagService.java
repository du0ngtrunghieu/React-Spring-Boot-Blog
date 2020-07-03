package com.trunghieu.api.tag;

import com.trunghieu.common.dto.ApiStatusDto;
import com.trunghieu.common.dto.PaginationDto;
import com.trunghieu.common.dto.TagRequestDto;
import com.trunghieu.common.exception.BadRequestException;
import com.trunghieu.common.exception.ResourceNotFoundException;
import com.trunghieu.common.model.Tag;
import com.trunghieu.common.repository.TagRepository;
import com.trunghieu.common.response.PagedResponse;
import com.trunghieu.common.response.TagResponse;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 25/5/2020.
 * Class: TagService.java
 * By : Admin
 */
@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public PagedResponse<TagResponse> getAllTags(PaginationDto paginationDto){
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize(), Sort.Direction.DESC, "createdAt");
        Page<Tag> tags = tagRepository.findALlByOrderByIdDesc(pageable);
        if(tags.getNumberOfElements() == 0){
            return new PagedResponse<>(Collections.emptyList(),tags.getNumber(),tags.getSize(),
                    tags.getTotalElements(),tags.getTotalPages(),tags.isLast()
            );
        }
        List<TagResponse> tagResponses = tags.stream()
                .map(tag->{
                    TagResponse tagResponse = new TagResponse();
                    BeanUtils.copyProperties(tag,tagResponse);
                    return tagResponse;
                }).collect(Collectors.toList());
        return new PagedResponse<>(tagResponses,tags.getNumber(),tags.getSize(),
                tags.getTotalElements(),tags.getTotalPages(),tags.isLast());
    }
    public List<TagResponse> getAllTagNoPagination(){
        List<Tag> tagResponses = tagRepository.findAllByOrderByIdDesc();
        return tagResponses.stream().map(tag -> {
            TagResponse tagResponse = new TagResponse();
            BeanUtils.copyProperties(tag,tagResponse);
            return tagResponse;
        }).collect(Collectors.toList());

    }
    public ApiStatusDto createTag(TagRequestDto tagRequestDto) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagRequestDto,tag);
        Tag rs =  tagRepository.save(tag);
        return new ApiStatusDto(true, MessageConstants.TAG_REGISTER_SUCCESS,rs);
    }
    public ApiStatusDto deleteTags(List<Long> ids){
        List<TagResponse> tagResponses = new ArrayList<>();
        ids.forEach(id -> {
           Optional<Tag> tag = tagRepository.findById(id);
           if(tag.isPresent()){
               TagResponse tagResponse = new TagResponse();
               BeanUtils.copyProperties(tag,tagResponse);
               tagResponses.add(tagResponse);
               tagRepository.deleteById(id);
           }
        });
        return new ApiStatusDto(true,MessageConstants.TAG_DELETE_SUCCESS,tagResponses);
    }
    public ApiStatusDto updateTag(Long id,TagRequestDto tagRequestDto){
        if(id == null){
            throw new BadRequestException("Không tìm thấy id");
        }
        Tag tag = tagRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Tag","Id",id)
        );
        BeanUtils.copyProperties(tag,tagRequestDto);
        Tag rs = tagRepository.save(tag);
        return new ApiStatusDto(true,MessageConstants.TAG_UPDATE_SUCCESS,rs);
    };
}
