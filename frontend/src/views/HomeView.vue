<template>
  <div class="home">
    <div class="header">
      <h1>즐거운 학습의 시작! 🎨</h1>
      <p class="subtitle">함께 배우고 성장하는 즐거운 공간</p>
    </div>
    <div class="button-container">
      <router-link to="/teacherlogin" class="login-button teacher">
        <span class="button-icon">👩‍🏫</span>
        <div class="button-content">
          <span class="button-title">학부모 로그인</span>
          <span class="button-description">학부모로 입장하기</span>
        </div>
      </router-link>
      <router-link to="/studentlogin" class="login-button student">
        <span class="button-icon">👩‍🎓</span>
        <div class="button-content">
          <span class="button-title">학생 로그인</span>
          <span class="button-description">학생으로 입장하기</span>
        </div>
      </router-link>
    </div>
    <div class="decoration">
      <span class="deco-item">✨</span>
      <span class="deco-item">🌟</span>
      <span class="deco-item">⭐</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 40px 20px;
  background-color: #f0f7ff;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(255,255,255,0.6) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255,255,255,0.6) 0%, transparent 20%);
  position: relative;
  overflow: hidden;
}

.header {
  text-align: center;
  margin-bottom: 60px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 15px;
  font-size: 3.2em;
  text-align: center;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
}

.subtitle {
  color: #5c6b7a;
  font-size: 1.5em;
  margin-top: 10px;
}

.button-container {
  display: flex;
  flex-direction: column;
  gap: 25px;
  width: 100%;
  max-width: 400px;
  position: relative;
  z-index: 1;
}

.login-button {
  padding: 25px 30px;
  font-size: 1.2em;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  text-align: left;
  background-color: white;
  color: #2c3e50;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 20px;
  border: 3px solid transparent;
}

.button-icon {
  font-size: 2.5em;
  transition: transform 0.3s ease;
}

.button-content {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.button-title {
  font-size: 1.3em;
  font-weight: bold;
  color: #2c3e50;
}

.button-description {
  font-size: 0.9em;
  color: #5c6b7a;
}

.login-button:hover {
  transform: translateY(-5px) scale(1.02);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
  border-color: #4CAF50;
}

.login-button:hover .button-icon {
  transform: scale(1.1) rotate(5deg);
}

.decoration {
  position: absolute;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.deco-item {
  position: absolute;
  font-size: 2em;
  animation: float 3s ease-in-out infinite;
}

.deco-item:nth-child(1) {
  top: 20%;
  left: 15%;
  animation-delay: 0s;
}

.deco-item:nth-child(2) {
  top: 30%;
  right: 15%;
  animation-delay: 1s;
}

.deco-item:nth-child(3) {
  bottom: 20%;
  left: 50%;
  animation-delay: 2s;
}

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(10deg); }
}

@media (max-width: 768px) {
  h1 {
    font-size: 2.5em;
  }

  .subtitle {
    font-size: 1.2em;
  }

  .login-button {
    padding: 20px 25px;
  }

  .button-icon {
    font-size: 2em;
  }
}
</style>