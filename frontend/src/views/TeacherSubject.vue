<template>
  <div class="subject-view">
    <div class="content-container">
      <h1>과목을 선택해주세요 📚</h1>
      <div class="subject-grid">
        <button 
          v-for="subject in subjects" 
          :key="subject.subject_id"
          @click="selectSubject(subject.subject_id, subject.subject_name)"
          class="subject-button"
        >
          <span class="subject-icon">
            {{ getSubjectIcon(subject.subject_name) }}
          </span>
          {{ subject.subject_name }}
        </button>
      </div>
      <button @click="goBack" class="back-button">
        <span class="button-icon">👈</span>
        돌아가기
      </button>
    </div>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'TeacherSubject',
  data() {
    return {
      subjects: []
    }
  },
  methods: {
    async fetchSubjects() {
      try {
        const response = await axiosInst.get('/subjects');
        this.subjects = response.data;
      } catch (error) {
        console.error('과목 목록 조회 실패:', error);
      }
    },
    selectSubject(subjectId, subjectName) {
      this.$router.push({
        name: 'teacherscore',
        query: { 
          subjectId: subjectId,
          subjectName: subjectName
        }
      });
    },
    goBack() {
      this.$router.go(-1);
    },
    getSubjectIcon(subjectName) {
      const icons = {
        '국어': '📖',
        '수학': '🔢',
        '영어': '🌏',
        '과학': '🔬',
        '사회': '🌍'
      };
      return icons[subjectName] || '📚';
    }
  },
  created() {
    this.fetchSubjects();
  }
}
</script>

<style scoped>
.subject-view {
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

.content-container {
  width: 100%;
  max-width: 800px;
}

h1 {
  color: #5f3dc4;
  margin-bottom: 2.5rem;
  font-size: 2.2rem;
  font-weight: 600;
  text-align: center;
  text-shadow: 2px 2px 4px rgba(95, 61, 196, 0.1);
}

.subject-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1.2rem;
  margin-bottom: 2.5rem;
}

.subject-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  padding: 1.5rem;
  font-size: 1.15rem;
  font-weight: 600;
  color: #495057;
  background-color: white;
  border: none;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 4px 12px rgba(95, 61, 196, 0.1);
}

.subject-icon {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.subject-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(95, 61, 196, 0.15);
  background-color: #7950f2;
  color: white;
}

.subject-button:active {
  transform: translateY(-1px);
}

.back-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  width: 200px;
  margin: 0 auto;
  padding: 1rem 1.5rem;
  font-size: 1.1rem;
  font-weight: 600;
  color: white;
  background-color: #9775fa;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 4px 12px rgba(151, 117, 250, 0.2);
}

.back-button:hover {
  background-color: #845ef7;
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(151, 117, 250, 0.25);
}

.back-button:active {
  transform: translateY(0);
}

.button-icon {
  font-size: 1.3rem;
}

@media (max-width: 768px) {
  .content-container {
    padding: 1rem;
  }

  h1 {
    font-size: 2rem;
    margin-bottom: 2rem;
  }

  .subject-button {
    padding: 1.25rem;
    font-size: 1.1rem;
  }

  .subject-icon {
    font-size: 2rem;
  }
}
</style> 