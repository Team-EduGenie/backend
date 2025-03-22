<template>
  <div class="teacher-login">
    <div class="login-container">
      <h1>학부모 로그인 🎓</h1>
      <div class="login-form">
        <div class="form-group">
          <label for="teacherName">
            <span class="label-icon">👩‍🏫</span>
            학부모 이름
          </label>
          <input 
            type="text" 
            id="teacherName" 
            v-model="teacherName" 
            placeholder="이름을 입력해주세요"
            @keyup.enter="handleLogin"
            required
          >
        </div>
        <button @click="handleLogin" class="submit-button">
          <span class="button-icon">✨</span>
          로그인하기
        </button>
        <div v-if="lastTeacherName" class="recent-login">
          <p class="recent-text">최근 로그인한 학부모</p>
          <button @click="loginWithRecentUser" class="recent-button">
            <span class="recent-icon">👋</span>
            {{ lastTeacherName }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'TeacherLogin',
  data() {
    return {
      teacherName: '',
      lastTeacherName: ''
    }
  },
  methods: {
    async handleLogin() {
      if (!this.teacherName.trim()) {
        alert('이름을 입력해주세요.');
        return;
      }

      try {
        const response = await axiosInst.post('/teachers/', {
          teacherName: this.teacherName.trim()
        });

        localStorage.setItem('lastTeacherName', this.teacherName.trim());
        localStorage.setItem('teacherId', response.data.teacherId);
        this.$router.push('/teachermenu');
      } catch (error) {
        console.error('Error:', error);
        alert('서버 오류가 발생했습니다.');
      }
    },
    async loginWithRecentUser() {
      this.teacherName = this.lastTeacherName;
      await this.handleLogin();
    }
  },
  created() {
    this.lastTeacherName = localStorage.getItem('lastTeacherName');
  }
}
</script>

<style scoped>
.teacher-login {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f3f0ff;
  background-image: 
    radial-gradient(circle at 20% 20%, rgba(180, 160, 255, 0.2) 0%, transparent 30%),
    radial-gradient(circle at 80% 80%, rgba(200, 180, 255, 0.2) 0%, transparent 30%);
  padding: 2rem;
}

.login-container {
  width: 100%;
  max-width: 400px;
}

h1 {
  color: #5f3dc4;
  margin-bottom: 2rem;
  font-size: 2.2rem;
  font-weight: 600;
  text-align: center;
  text-shadow: 2px 2px 4px rgba(95, 61, 196, 0.1);
}

.login-form {
  background-color: white;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(95, 61, 196, 0.1);
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
  color: #5f3dc4;
  font-weight: 500;
  font-size: 1.1rem;
}

.label-icon {
  font-size: 1.4rem;
}

input {
  width: 100%;
  padding: 0.875rem 1.25rem;
  border: 2px solid #e5dbff;
  border-radius: 12px;
  font-size: 1rem;
  color: #495057;
  transition: all 0.2s ease;
  background-color: #fbfaff;
}

input:focus {
  outline: none;
  border-color: #7950f2;
  box-shadow: 0 0 0 4px rgba(121, 80, 242, 0.1);
  background-color: white;
}

input::placeholder {
  color: #a8a4b8;
}

.submit-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  width: 100%;
  padding: 1rem;
  background-color: #7950f2;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 4px 12px rgba(121, 80, 242, 0.2);
}

.submit-button:hover {
  background-color: #6741d9;
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(121, 80, 242, 0.25);
}

.submit-button:active {
  transform: translateY(0);
}

.button-icon {
  font-size: 1.3rem;
}

.recent-login {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.recent-text {
  color: #5f3dc4;
  font-size: 1.1em;
  margin-bottom: 5px;
}

.recent-button {
  padding: 12px 25px;
  font-size: 1.2em;
  background-color: #f3f0ff;
  color: #5f3dc4;
  border: 2px solid #e5dbff;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 2px 8px rgba(95, 61, 196, 0.2);
}

.recent-button:hover {
  background-color: #7950f2;
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(95, 61, 196, 0.3);
}

.recent-icon {
  font-size: 1.2em;
}

@media (max-width: 768px) {
  .login-container {
    padding: 1rem;
  }

  .login-form {
    padding: 1.5rem;
  }

  h1 {
    font-size: 2rem;
  }
}
</style> 