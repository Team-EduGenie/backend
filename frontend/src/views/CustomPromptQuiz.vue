<template>
  <div class="custom-prompt-quiz">
    <div class="header">
      <h1>내가 만드는 문제! 🎨</h1>
      <p class="subtitle">어떤 문제를 만들어볼까요?</p>
    </div>
    <div class="prompt-input-section">
      <div class="input-group">
        <label for="prompt">
          <span class="label-icon">💡</span>
          원하는 문제를 설명해주세요
        </label>
        <div class="textarea-wrapper">
          <textarea 
            id="prompt"
            v-model="userPrompt"
            placeholder="예시: 8살 아이를 위한 재미있는 과학 문제를 만들어주세요!"
            rows="3"
          ></textarea>
        </div>
      </div>
      <button @click="generateQuiz" :disabled="!userPrompt.trim()" class="generate-button">
        <span class="button-icon">✨</span>
        <span class="button-text">문제 만들기</span>
      </button>
    </div>

    <div v-if="currentQuiz" class="quiz-content">
      <div class="question-box">
        <h2>문제 💭</h2>
        <p class="question-text">{{ currentQuiz.question }}</p>
      </div>
      <div class="answer-box">
        <div class="input-wrapper">
          <span class="input-icon">✏️</span>
          <input 
            type="text" 
            v-model="userAnswer" 
            placeholder="정답을 입력해주세요!"
            @keyup.enter="checkAnswer"
          >
        </div>
        <div class="button-wrapper">
          <button @click="checkAnswer" :disabled="isCorrect" class="check-button">
            <span class="button-icon">✨</span>
            <span class="button-text">정답 확인</span>
          </button>
          <button @click="showTrueAnswer" :disabled="showAnswer" class="show-answer-button">
            <span class="button-icon">🔍</span>
            <span class="button-text">답 보기</span>
          </button>
        </div>
      </div>
      <div v-if="isAnswered" class="result-box">
        <p :class="{ 'correct': isCorrect, 'incorrect': !isCorrect }">
          <span class="result-icon">{{ isCorrect ? '🎉' : '💫' }}</span>
          {{ isCorrect ? '와아! 정답이에요!' : '아쉽네요~ 다시 한번 생각해볼까요?' }}
        </p>
      </div>
      <div v-if="showAnswer" class="answer-display">
        <p>정답은 바로... <span class="answer-text">{{ currentQuiz.answer }}</span> 이에요!</p>
      </div>
      <div class="button-group">
        <button @click="generateNewQuiz" class="next-button">
          <span class="button-icon">🎲</span>
          <span class="button-text">새로운 문제 만들기</span>
        </button>
        <button @click="goToMenu" class="end-button">
          <span class="button-icon">🏠</span>
          <span class="button-text">끝내기</span>
        </button>
      </div>
    </div>
    <div class="decoration">
      <span class="deco-item star1">⭐</span>
      <span class="deco-item star2">✨</span>
      <span class="deco-item heart">💖</span>
      <span class="deco-item puzzle">🧩</span>
      <span class="deco-item magic">🎨</span>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'CustomPromptQuiz',
  data() {
    return {
      userPrompt: '',
      currentQuiz: null,
      userAnswer: '',
      isAnswered: false,
      isCorrect: false,
      showAnswer: false
    }
  },
  methods: {
    async generateQuiz() {
      try {
        console.log('문제 생성 요청 시작');
        const response = await axiosInst.post('api/custom-quiz/generate-with-prompt', {
          prompt: this.userPrompt
        });
        console.log('문제 생성 응답:', response.data);
        this.currentQuiz = response.data;
        this.userAnswer = '';
        this.isAnswered = false;
        this.isCorrect = false;
        this.showAnswer = false;
      } catch (error) {
        console.error('문제 생성 실패:', error.response ? error.response.data : error.message);
        alert('문제 생성에 실패했습니다. 다시 시도해주세요.');
      }
    },
    generateNewQuiz() {
      this.currentQuiz = null;
      this.userPrompt = '';
      this.userAnswer = '';
      this.isAnswered = false;
      this.isCorrect = false;
      this.showAnswer = false;
    },
    checkAnswer() {
      if (!this.userAnswer.trim()) return;
      
      const isCorrect = this.userAnswer.toLowerCase() === this.currentQuiz.answer.toLowerCase();
      this.isCorrect = isCorrect;
      this.isAnswered = true;
      
      if (!isCorrect) {
        this.userAnswer = '';
      }
    },
    showTrueAnswer() {
      console.log('답보기 버튼 클릭됨');
      this.showAnswer = true;
      console.log('showAnswer 상태:', this.showAnswer);
      this.isCorrect = false;
      this.isAnswered = true;
      console.log('답 표시 상태:', this.currentQuiz.answer);
      console.log('현재 퀴즈 상태:', this.currentQuiz);
    },
    goToMenu() {
      this.$router.push('/studentmenu');
    }
  }
}
</script>

<style scoped>
.custom-prompt-quiz {
  display: flex;
  flex-direction: column;
  align-items: center;
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
  display: flex;
  flex-direction: column;
  align-items: center;
}

h1 {
  color: #ff6b6b;
  margin-bottom: 10px;
  font-size: 3em;
  text-align: center;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(255,107,107,0.2);
  width: fit-content;
}

.subtitle {
  color: #ff8787;
  font-size: 1.6em;
  margin-top: 10px;
  font-weight: 500;
  width: fit-content;
}

.prompt-input-section {
  background-color: white;
  padding: 30px;
  border-radius: 25px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 600px;
  margin-bottom: 30px;
  position: relative;
  z-index: 1;
}

.input-group {
  margin-bottom: 20px;
}

label {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 15px;
  color: #2c3e50;
  font-size: 1.2em;
  font-weight: 500;
}

.label-icon {
  font-size: 1.4em;
}

.textarea-wrapper {
  position: relative;
  background-color: #fff9f9;
  border-radius: 15px;
  padding: 3px;
}

textarea {
  width: 100%;
  padding: 15px;
  font-size: 1.1em;
  border: 3px solid #ffc9c9;
  border-radius: 15px;
  resize: vertical;
  min-height: 100px;
  background-color: white;
  color: #495057;
  transition: all 0.3s ease;
}

textarea:focus {
  outline: none;
  border-color: #ff8787;
  box-shadow: 0 0 15px rgba(255,135,135,0.3);
}

.generate-button {
  width: 100%;
  padding: 15px;
  font-size: 1.2em;
  background-color: #ff8787;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  box-shadow: 0 5px 15px rgba(255,135,135,0.4);
}

.generate-button:hover:not(:disabled) {
  background-color: #ff6b6b;
  transform: translateY(-3px);
}

.generate-button:disabled {
  background-color: #ffc9c9;
  cursor: not-allowed;
}

.quiz-content {
  background-color: white;
  padding: 30px;
  border-radius: 25px;
  box-shadow: 0 10px 20px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 600px;
  position: relative;
  z-index: 1;
}

.question-box {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #fff9f9;
  border-radius: 15px;
  border: 3px solid #ffd8d8;
}

.question-box h2 {
  color: #ff6b6b;
  margin-bottom: 15px;
  font-size: 1.8em;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.question-text {
  font-size: 1.3em;
  color: #2c3e50;
  line-height: 1.6;
}

.answer-box {
  display: flex;
  flex-direction: column;
  gap: 15px;
  align-items: center;
}

.input-wrapper {
  position: relative;
  width: 100%;
  max-width: 400px;
}

.input-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.5em;
  pointer-events: none;
}

input {
  width: 100%;
  padding: 15px 15px 15px 50px;
  font-size: 1.2em;
  border: 3px solid #ffc9c9;
  border-radius: 25px;
  background-color: white;
  color: #495057;
  transition: all 0.3s ease;
}

input:focus {
  outline: none;
  border-color: #ff8787;
  box-shadow: 0 0 15px rgba(255,135,135,0.3);
}

.button-wrapper {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

button {
  padding: 12px 25px;
  font-size: 1.1em;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  background-color: white;
  color: #2c3e50;
  border: 3px solid transparent;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.button-icon {
  font-size: 1.2em;
}

.check-button { background-color: #ff8787; color: white; }
.check-button:hover { background-color: #ff6b6b; transform: translateY(-3px); }

.show-answer-button { background-color: #74c0fc; color: white; }
.show-answer-button:hover { background-color: #4dabf7; transform: translateY(-3px); }

.next-button { background-color: #63e6be; color: white; }
.next-button:hover { background-color: #38d9a9; transform: translateY(-3px); }

.end-button { background-color: #ffa8a8; color: white; }
.end-button:hover { background-color: #ff8787; transform: translateY(-3px); }

.result-box {
  margin: 20px 0;
  padding: 15px;
  border-radius: 15px;
  font-size: 1.2em;
}

.result-icon {
  font-size: 1.4em;
  margin-right: 8px;
}

.correct {
  color: #40c057;
  background-color: #ebfbee;
}

.incorrect {
  color: #ff6b6b;
  background-color: #fff5f5;
}

.answer-display {
  margin: 20px 0;
  padding: 15px;
  background-color: #e3f2fd;
  border-radius: 15px;
  font-size: 1.2em;
  color: #1976D2;
}

.answer-text {
  font-weight: bold;
  color: #1976D2;
}

.decoration {
  position: absolute;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.deco-item {
  position: absolute;
  font-size: 2.5em;
  animation: float 4s ease-in-out infinite;
}

.star1 { top: 15%; left: 10%; animation-delay: 0s; }
.star2 { top: 25%; right: 15%; animation-delay: 1s; }
.heart { bottom: 20%; left: 15%; animation-delay: 2s; }
.puzzle { bottom: 30%; right: 10%; animation-delay: 1.5s; }
.magic { top: 40%; left: 50%; animation-delay: 0.5s; }

@keyframes float {
  0%, 100% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(10deg); }
}

@media (max-width: 768px) {
  h1 {
    font-size: 2.5em;
  }

  .subtitle {
    font-size: 1.3em;
  }

  .question-text {
    font-size: 1.2em;
  }

  input, textarea {
    font-size: 1.1em;
  }

  button {
    padding: 10px 20px;
    font-size: 1em;
  }

  .deco-item {
    font-size: 2em;
  }
}
</style> 