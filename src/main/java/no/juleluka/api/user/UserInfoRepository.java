package no.juleluka.api.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
/*
    UserInfo findByUsername(String username);

    default UserInfo getOrCreate(String username) {
        UserInfo userInfo = this.findById(username);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUsername(username);
            this.save(userInfo);
        }

        return userInfo;
    }
*/
}
