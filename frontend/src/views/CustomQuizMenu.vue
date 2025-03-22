<template>
  <div class="custom-quiz-menu">
    <div class="header">
      <h1>특별한 문제를 풀어볼까요? 🌟</h1>
      <p class="subtitle">재미있는 퀴즈가 기다리고 있어요!</p>
    </div>
    <div class="menu-buttons">
      <button @click="goToRiddle" class="menu-button riddle">
        <span class="button-icon">🎯</span>
        <div class="button-content">
          <span class="button-title">수수께끼 문제</span>
          <span class="button-description">재미있는 수수께끼를 풀어보아요</span>
        </div>
      </button>
      <button @click="goToCooking" class="menu-button cooking">
        <span class="button-icon">👨‍🍳</span>
        <div class="button-content">
          <span class="button-title">꼬마 요리사 교실</span>
          <span class="button-description">맛있는 요리 퀴즈를 풀어보아요</span>
        </div>
      </button>
      <button @click="goToMenu" class="menu-button back">
        <span class="button-icon">🏠</span>
        <div class="button-content">
          <span class="button-title">돌아가기</span>
          <span class="button-description">메인 메뉴로 돌아갈래요</span>
        </div>
      </button>
    </div>
    <div class="decoration">
      <span class="deco-item puzzle">🧩</span>
      <span class="deco-item bulb">💡</span>
      <span class="deco-item star">⭐</span>
      <span class="deco-item brain">🧠</span>
      <span class="deco-item magic">✨</span>
      <span class="deco-item chef">🥘</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomQuizMenu',
  methods: {
    goToRiddle() {
      this.$router.push({
        path: '/custom-quiz',
        query: { type: 'riddle' }
      });
    },
    goToCooking() {
      this.$router.push({
        path: '/custom-quiz',
        query: { type: 'cooking' }
      });
    },
    goToMenu() {
      this.$router.push('/studentmenu');
    }
  }
}
</script>

<style scoped>
.custom-quiz-menu {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 40px 20px;
  background-color: #fff5f5;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(255,182,193,0.3) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255,218,185,0.3) 0%, transparent 20%);
  position: relative;
  overflow: hidden;
}

.header {
  text-align: center;
  margin-bottom: 40px;
}

h1 {
  color: #ff6b6b;
  font-size: 2.5em;
  margin-bottom: 10px;
  text-shadow: 2px 2px 4px rgba(255,107,107,0.2);
}

.subtitle {
  color: #ff8787;
  font-size: 1.2em;
}

.menu-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
  max-width: 500px;
  position: relative;
  z-index: 1;
}

.menu-button {
  padding: 20px;
  border: none;
  border-radius: 15px;
  background-color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.button-icon {
  font-size: 2em;
}

.button-content {
  text-align: left;
  flex-grow: 1;
}

.button-title {
  display: block;
  font-size: 1.3em;
  font-weight: bold;
  color: #343a40;
  margin-bottom: 5px;
}

.button-description {
  display: block;
  font-size: 0.9em;
  color: #868e96;
}

.riddle {
  border-left: 5px solid #ff6b6b;
}

.riddle:hover {
  background-color: #fff5f5;
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(255,107,107,0.2);
}

.cooking {
  border-left: 5px solid #51cf66;
}

.cooking:hover {
  background-color: #ebfbee;
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(81,207,102,0.2);
}

.back {
  border-left: 5px solid #339af0;
}

.back:hover {
  background-color: #e7f5ff;
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(51,154,240,0.2);
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
  animation: float 6s ease-in-out infinite;
}

.puzzle { top: 10%; left: 10%; animation-delay: 0s; }
.bulb { top: 20%; right: 15%; animation-delay: 1s; }
.star { bottom: 20%; left: 15%; animation-delay: 2s; }
.brain { bottom: 30%; right: 10%; animation-delay: 1.5s; }
.magic { top: 40%; left: 50%; animation-delay: 0.5s; }
.chef { top: 15%; right: 40%; animation-delay: 2.5s; }

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(10deg); }
}

@media (max-width: 768px) {
  h1 {
    font-size: 2em;
  }

  .subtitle {
    font-size: 1em;
  }

  .menu-button {
    padding: 15px;
  }

  .button-icon {
    font-size: 1.5em;
  }

  .button-title {
    font-size: 1.1em;
  }

  .button-description {
    font-size: 0.8em;
  }
}
</style> 