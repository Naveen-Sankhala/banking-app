package com.relx.banking.authservice.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.relx.banking.authservice.entity.UserLog;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {

	List<UserLog> findByUserIdOrderByUserLogIdDesc(Long userId);

	@Query("Select a from UserLog a where a.ipAddress=:remoteAddr and a.userId=:userId and TO_CHAR(a.refreshToken)=:token")
	Collection<UserLog> findByIpAddressAndUserIdAndRefreshToken(String remoteAddr, long userId, String token);

	@Query("Select a from UserLog a where TO_CHAR(a.refreshToken)=:refreshToken")
	UserLog findByRefreshToken(String refreshToken);

	@Query(value="select ISLOGGEDIN from USER_LOG where USERLOG_ID=(select max(USERLOG_ID) from USER_LOG a inner join users B on a.USER_ID=B.USER_ID and B.USER_NAME=:username )",nativeQuery = true)
	char isLoggedIn(String username);
	
	@Query(value="Select A.* FROM USER_LOG A INNER JOIN USERS B ON A.USER_ID=B.USER_ID AND B.USER_NAME=:serviceNo ORDER BY UserLog_Id DESC",nativeQuery = true)
	List<UserLog> findByServiceNo(String serviceNo);
	
	@Query("select max(loggedInTime) from UserLog where loggedInTime not in (select max(loggedInTime) from UserLog where userId=:userId GROUP by userId) and userId=:userId ")
	LocalDateTime getLastLoggedInTime(long userId);


}
