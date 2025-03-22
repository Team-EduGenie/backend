<template>
  <div class="student-select-view">
    <h1>자녀 성적 조회 🎓</h1>
    <p class="subtitle">성적을 확인할 자녀를 선택해주세요</p>

    <div class="students-grid">
      <div 
        v-for="student in students" 
        :key="student.studentId"
        @click="selectStudent(student)"
        class="student-card"
      >
        <div class="student-icon">👨‍🎓</div>
        <h2>{{ student.studentName }}</h2>
      </div>
    </div>

    <button @click="goBack" class="back-button">
      <span class="button-icon">👈</span>
      돌아가기
    </button>
  </div>
</template>

<script>
import axiosInst from '../axios'

export default {
  name: 'TeacherStudentSelect',
  data() {
    return {
      students: []
    }
  },
  methods: {
    async fetchStudents() {
      try {
        const teacherId = localStorage.getItem('teacherId');
        const response = await axiosInst.get(`api/students/teacher/${teacherId}`);
        this.students = response.data;
      } catch (error) {
        console.error('학생 목록 조회 실패:', error);
      }
    },
    selectStudent(student) {
      this.$router.push({
        path: '/teacherscore',
        query: { 
          studentId: student.studentId,
          studentName: student.studentName
        }
      });
    },
    goBack() {
      this.$router.go(-1);
    }
  },
  created() {
    this.fetchStudents();
  }
}
</script>

<style scoped>
.student-select-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 2rem;
  background-color: #f3f0ff;
  background-image: 
    radial-gradient(circle at 20% 20%, rgba(180, 160, 255, 0.2) 0%, transparent 30%),
    radial-gradient(circle at 80% 80%, rgba(200, 180, 255, 0.2) 0%, transparent 30%);
}

h1 {
  color: #5f3dc4;
  margin-bottom: 0.5rem;
  font-size: 2.2rem;
  font-weight: 600;
  text-align: center;
  text-shadow: 2px 2px 4px rgba(95, 61, 196, 0.1);
}

.subtitle {
  color: #7048e8;
  font-size: 1.2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.students-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
  width: 100%;
  max-width: 1000px;
  margin-bottom: 2rem;
}

.student-card {
  background-color: white;
  padding: 1.5rem;
  border-radius: 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 4px 12px rgba(95, 61, 196, 0.1);
}

.student-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(95, 61, 196, 0.15);
  background-color: #7950f2;
  color: white;
}

.student-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

h2 {
  margin: 0;
  font-size: 1.2rem;
  font-weight: 500;
}

.back-button {
  display: flex;
  align-items: center;
  gap: 0.75rem;
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

@media (max-width: 768px) {
  .students-grid {
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  }

  h1 {
    font-size: 2rem;
  }

  .subtitle {
    font-size: 1.1rem;
  }
}
</style> 