package org.quagh.blogbackend.services.tag;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.TagDTO;
import org.quagh.blogbackend.entities.PostTag;
import org.quagh.blogbackend.entities.Tag;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.PostTagRepository;
import org.quagh.blogbackend.repositories.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService{
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    @Override
    @Transactional
    public Tag addTag(TagDTO tagDTO){
        Tag newTag = new Tag();
        BeanUtils.copyProperties(tagDTO, newTag);
        newTag.setNumberOfPosts(0);
        return tagRepository.save(newTag);
    }

    @Override
    public List<Tag> getAllTags(){return tagRepository.findAll();}

    @Override
    public Tag getTagById(Long id) throws DataNotFoundException {
        return tagRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tag not found"));
    }

    @Override
    @Transactional
    public Tag deleteTag(Long id) throws ChangeSetPersister.NotFoundException{
        Tag tag = tagRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<PostTag> postTagList = postTagRepository.findPostTagByTag(tag);
        if(!postTagList.isEmpty()){
            throw new IllegalStateException("Cannot delete tag with associated post");
        }
        tagRepository.deleteById(id);
        return tag;
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, TagDTO tagDTO) throws DataNotFoundException{
        Tag existingTag = getTagById(id);
        BeanUtils.copyProperties(tagDTO, existingTag, "number_of_posts");
        return tagRepository.save(existingTag);
    }

}
