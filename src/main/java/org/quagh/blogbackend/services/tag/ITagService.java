package org.quagh.blogbackend.services.tag;

import org.quagh.blogbackend.dtos.TagDTO;
import org.quagh.blogbackend.entities.Tag;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface ITagService {
    Tag addTag(TagDTO tagDTO);

    List<Tag> getAllTags();

    Tag getTagById(Long id) throws DataNotFoundException;

    Tag deleteTag(Long id) throws ChangeSetPersister.NotFoundException;

    Tag updateTag(Long id, TagDTO tagDTO) throws DataNotFoundException;
}
