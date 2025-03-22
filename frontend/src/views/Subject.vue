<template>
  <div class="subject-view">
    <div class="header">
      <h1>과목을 선택해보세요! 📚</h1>
      <p class="subtitle">어떤 과목을 공부할까요?</p>
    </div>
    <div v-if="loading" class="loading">
      <div class="loading-spinner">🎈</div>
      잠시만 기다려주세요...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
      <button @click="fetchSubjects" class="retry-button">다시 시도</button>
    </div>
    <div v-else class="subject-grid">
      <div 
        v-for="subject in subjects" 
        :key="subject.subjectId"
        @click="handleSubjectClick(subject)"
        class="subject-card"
      >
        <div class="subject-icon">{{ getSubjectIcon(subject.subjectName) }}</div>
        <h2>{{ subject.subjectName }}</h2>
        <div class="subject-description">
          {{ getSubjectDescription(subject.subjectName) }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'Subject',
  data() {
    return {
      subjects: [],
      loading: false,
      error: null,
      isInitialized: false
    }
  },
  methods: {
    async fetchSubjects() {
      if (this.isInitialized) return;
      
      this.loading = true;
      this.error = null;
      try {
        const response = await axiosInst.get('/subjects');
        if (Array.isArray(response.data)) {
          this.subjects = response.data;
          console.log('Fetched subjects:', this.subjects); // 디버깅용 로그
          this.isInitialized = true;
        } else {
          throw new Error('Invalid response format');
        }
      } catch (error) {
        console.error('과목 목록을 가져오는데 실패했습니다:', error);
        this.error = '과목 목록을 가져오는데 실패했습니다. 다시 시도해주세요.';
      } finally {
        this.loading = false;
      }
    },
    handleSubjectClick(subject) {
      this.$router.push({
        path: '/unit',
        query: { subjectId: subject.subjectId }
      });
    },
    getSubjectIcon(subjectName) {
      const icons = {
        '국어': '📚',
        '수학': '🔢',
        '사회': '🌏',
        '과학': '🔬',
        '영어': '🌎'
      };
      console.log('Subject name:', subjectName); // 디버깅용 로그
      return icons[subjectName] || '📖';
    },
    getSubjectDescription(subjectName) {
      const descriptions = {
        '국어': '재미있는 이야기와 글쓰기를 배워요!',
        '수학': '신기한 숫자의 세계를 탐험해요!',
        '사회': '우리 주변의 세상을 알아가요!',
        '과학': '신비한 자연의 비밀을 발견해요!',
        '영어': '새로운 언어로 대화해보아요!'
      };
      return descriptions[subjectName] || '새로운 것을 배워보아요!';
    }
  },
  mounted() {
    this.fetchSubjects();
  }
}
</script>

<style scoped>
.subject-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 40px 20px;
  background-color: #f0f7ff;
  background-image: 
    radial-gradient(circle at 10% 20%, rgba(255,255,255,0.6) 0%, transparent 20%),
    radial-gradient(circle at 90% 80%, rgba(255,255,255,0.6) 0%, transparent 20%);
}

.header {
  text-align: center;
  margin-bottom: 40px;
}

h1 {
  color: #2c3e50;
  margin-bottom: 10px;
  font-size: 2.8em;
  text-align: center;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
}

.subtitle {
  color: #5c6b7a;
  font-size: 1.4em;
  margin-top: 10px;
}

.subject-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  padding: 20px;
}

.subject-card {
  background-color: white;
  border-radius: 20px;
  padding: 30px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  border: 3px solid transparent;
}

.subject-card:hover {
  transform: translateY(-10px) scale(1.02);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
  border-color: #4CAF50;
}

.subject-icon {
  font-size: 4em;
  margin-bottom: 10px;
  transition: transform 0.3s ease;
}

.subject-card:hover .subject-icon {
  transform: scale(1.1) rotate(5deg);
}

h2 {
  margin: 0;
  color: #2c3e50;
  font-size: 1.8em;
  font-weight: bold;
}

.subject-description {
  color: #5c6b7a;
  font-size: 1.1em;
  line-height: 1.4;
  margin-top: 10px;
}

.loading {
  text-align: center;
  padding: 40px;
  font-size: 1.4em;
  color: #5c6b7a;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.loading-spinner {
  font-size: 3em;
  animation: float 2s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-20px); }
}

.error {
  text-align: center;
  padding: 30px;
  color: #e74c3c;
  background-color: #fdf0ef;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  max-width: 400px;
  margin: 0 auto;
}

.retry-button {
  padding: 12px 25px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1.1em;
  transition: all 0.3s ease;
}

.retry-button:hover {
  background-color: #45a049;
  transform: scale(1.05);
}

@media (max-width: 768px) {
  .subject-grid {
    grid-template-columns: 1fr;
    max-width: 400px;
  }

  h1 {
    font-size: 2.2em;
  }

  .subtitle {
    font-size: 1.2em;
  }
}
</style> 