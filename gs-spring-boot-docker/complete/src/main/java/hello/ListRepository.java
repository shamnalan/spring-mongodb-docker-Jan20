package hello;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListRepository extends MongoRepository<WatchList, String> {

    public WatchList findByListName(String listName);
    public List<WatchList> findByListType(String listType);

}