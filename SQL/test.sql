select * from person;
select * from role;
select * from pers_role;


select * from person where id ='user001';


INSERT INTO role (name) VALUES
                            ('SUPER_ADMIN'),
                            ('ADMIN'),
                            ('USER');




INSERT INTO person (id, password, naverId, persName, email, birth, profileImage, age, gender, authType, accessToken, refreshToken, lastLogin)
VALUES
    ('user001', '1234', NULL, '홍길동', 'hong@example.com', '900515-1', NULL, '30-39', 'M', 'LOCAL', NULL, NULL, NOW()), -- 일반 회원가입 사용자
    ('user002', '1234', NULL, '김철수', 'kim@example.com', '850820-1', 'https://example.com/profile1.jpg', '40-49', 'M', 'LOCAL', NULL, NULL, NOW()), -- 일반 회원가입 사용자
    ('user003', '1234', 'naver_123456', '이영희', 'lee@naver.com', '951205-2', 'https://example.com/profile2.jpg', '20-29', 'F', 'NAVER', 'oauth_token_abc123', 'refresh_token_def456', NOW()), -- 네이버 OAuth 사용자
    ('user004', '1234', 'naver_789101', '박지성', 'park@naver.com', '820225-1', 'https://example.com/profile3.jpg', '40-49', 'M', 'NAVER', 'oauth_token_xyz789', 'refresh_token_ghi012', NOW()); -- 네이버 OAuth 사용자



INSERT INTO pers_role (person_id, role_id) VALUES
                                               (1, 1), -- 홍길동 → SUPER_ADMIN ,ADMIN , USER
                                               (1, 2),
                                               (1, 3),
                                               (2, 3), -- 김철수 → USER
                                               (3, 3), -- 이영희 → USER
                                               (4, 2),
                                               (4,3); -- 박지성 → ADMIN , USER
