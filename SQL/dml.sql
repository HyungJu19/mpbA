
drop table pers_role;
drop table role;
drop table person;


CREATE TABLE person (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username varchar(255) UNIQUE not null ,            -- 고유 식별자 (기본 키)
                        password VARCHAR(255) NULL,                     -- 비밀번호 (일반 회원가입 사용자만 사용)
                        naverId VARCHAR(100) UNIQUE NULL,               -- 네이버 고유 ID (OAuth 사용자만 사용)
                        persName VARCHAR(50) NOT NULL,                  -- 사용자 이름 (필수 입력)
                        email VARCHAR(100) UNIQUE NOT NULL,             -- 이메일 (필수 입력, 중복 불가)
                        birth varchar(20) UNIQUE NULL,                  -- 생년월일
                        phone varchar(20) not null ,                    -- 핸드폰번호
                        carrier varchar(50) not null ,                  -- 통신사
                        profileImage VARCHAR(255) NULL,                 -- 프로필 이미지 URL (옵션,네이버든 일반이든 가능)
                        age VARCHAR(10) NULL,                           -- 연령대 (네이버 OAuth or 일반(생년월일기반))
                        gender ENUM('M', 'F', 'U') NULL,                -- 성별 (M: 남성, F: 여성, U: 알 수 없음(예외용))
                        agreement varchar(10) not null,                 -- 약관동의
                        authType ENUM('LOCAL', 'NAVER') NOT NULL,       -- 로그인 유형 (LOCAL: 일반 회원가입, NAVER: 네이버 로그인)
                        accessToken VARCHAR(255) NULL,                  -- OAuth 인증 토큰 (네이버 OAuth 사용자만 해당)
                        refreshToken VARCHAR(255) NULL,                 -- OAuth 갱신 토큰
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- 계정 생성 시간
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정 시간
                        lastLogin TIMESTAMP NULL,                      -- 최근 로그인 시간
                        deleted_at TIMESTAMP NULL                      -- 계정 삭제 시간 (NULL이면 활성 상태)


);

CREATE TABLE role (
                      id INT AUTO_INCREMENT PRIMARY KEY, -- 권한 ID (PK)
                      name VARCHAR(20) NOT NULL UNIQUE   -- 권한명 (예: 'SUPER_ADMIN', 'ADMIN', 'USER')
);



CREATE TABLE pers_role (
                      person_id INT,              -- 사용자 ID
                      role_id INT,                -- 권한 ID
                      PRIMARY KEY (person_id, role_id), -- 복합 키 (사용자 1명당 여러 역할 가능)
                      FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                      FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);
