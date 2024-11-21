const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const cors = require('cors');
const path = require('path');
const dotenv = require('dotenv'); // dotenv 모듈 추가
dotenv.config(); // .env 파일 로드

const app = express();
const PORT = 3001;  // 백엔드 포트 3001로 설정

const JWT_SECRET = process.env.JWT_SECRET;  

const users = [];  

// Middleware 설정
app.use(cors({ origin: 'http://localhost:3000' }));  // 프론트엔드 포트가 3000이면 그 도메인만 허용
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// 로그인 처리
app.post('/api/login', async (req, res) => {
    try {
        const { id, password } = req.body;
        const user = users.find(u => u.id === id);

        if (!user) {
            return res.status(400).json({ message: 'ID가 존재하지 않습니다.' });
        }

        const match = await bcrypt.compare(password, user.password);
        if (!match) {
            return res.status(400).json({ message: '비밀번호가 일치하지 않습니다.' });
        }

        // JWT 토큰 발급
        const token = jwt.sign({ id: user.id }, JWT_SECRET, { expiresIn: '1h' });

        // 로그인 성공 후 토큰을 반환
        res.json({ message: '로그인 성공!', token });
    } catch (error) {
        res.status(500).json({ message: '서버 오류가 발생했습니다.' });
    }
});

// 회원가입 처리
app.post('/api/register', async (req, res) => {
    try {
        const { id, password1 } = req.body;

        const existingUser = users.find(u => u.id === id);
        if (existingUser) {
            return res.status(400).json({ message: 'ID가 이미 존재합니다.' });
        }

        const hashedPassword = await bcrypt.hash(password1, 10);

        const newUser = {
            id,
            password: hashedPassword,
        };

        users.push(newUser);

        res.json({ message: '회원가입 성공!' });
    } catch (error) {
        res.status(500).json({ message: '서버 오류가 발생했습니다.' });
    }
});

// 아이디 중복 확인
app.get('/api/check-id', (req, res) => {
    const { userid } = req.query;
    const existingUser = users.find(u => u.id === userid);

    if (existingUser) {
        return res.json({ exists: true });
    } else {
        return res.json({ exists: false });
    }
});

// 정적 파일 제공
app.use(express.static(path.join(__dirname, '..', 'client', 'build')));
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'client', 'build', 'index.html'));
});

// 서버 실행
app.listen(PORT, () => {
    console.log(`서버가 http://localhost:${PORT} 실행 중입니다.`);
});
