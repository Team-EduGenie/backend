<template>
  <div class="score-view">
    <h1>{{ $route.query.studentName }}님의 성적 조회</h1>
    
    <div class="filter-section">
      <select v-model="selectedSubject" class="subject-filter">
        <option value="all">전체 과목</option>
        <option v-for="subject in subjects" :key="subject.subjectId" :value="subject.subjectId">
          {{ subject.subjectName }}
        </option>
      </select>
    </div>

    <div class="content-wrapper">
      <div class="score-table">
        <table>
          <thead>
            <tr>
              <th>과목</th>
              <th>단원</th>
              <th>난이도</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="score in filteredScores" :key="score.unitName">
              <td>{{ getSubjectName(score.subjectId) }}</td>
              <td>{{ score.unitName }}</td>
              <td :class="getDifficultyClass(score.difficulty)">{{ score.difficulty }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="chart-container">
        <h2>과목별 성적 분포</h2>
        <div class="chart-wrapper">
          <canvas ref="chartCanvas"></canvas>
        </div>
      </div>
    </div>

    <button @click="goBack" class="back-button">돌아가기</button>
  </div>
</template>

<script>
import axiosInst from '../axios'
import { 
  Chart, 
  BarElement, 
  BarController,
  CategoryScale, 
  LinearScale, 
  Tooltip, 
  Legend 
} from 'chart.js'

Chart.register(
  BarElement,
  BarController,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend
)

export default {
  name: 'TeacherScore',
  data() {
    return {
      scores: [],
      subjects: [],
      chart: null,
      selectedSubject: 'all'
    }
  },
  computed: {
    filteredScores() {
      if (this.selectedSubject === 'all') {
        return this.scores;
      }
      return this.scores.filter(score => score.subjectId === this.selectedSubject);
    }
  },
  watch: {
    selectedSubject() {
      this.updateChart();
    }
  },
  methods: {
    getSubjectName(subjectId) {
      const subject = this.subjects.find(s => s.subjectId === subjectId);
      return subject ? subject.subjectName : '';
    },
    getDifficultyLevel(difficulty) {
      if (difficulty === 3) return '상';
      if (difficulty === 2) return '중';
      if (difficulty === 1) return '하';
      return '미흡';
    },
    getAchievementLevel(percentage) {
      if (percentage >= 76) return '매우 우수';
      if (percentage >= 51) return '우수';
      if (percentage >= 21) return '보통';
      return '미흡';
    },
    goBack() {
      this.$router.go(-1);
    },
    async fetchScores() {
      try {
        const studentId = this.$route.query.studentId;
        const teacherId = localStorage.getItem('teacherId');
        
        if (!studentId) {
          this.$router.push('/teacherstudentselect');
          return;
        }

        console.log('학생 ID:', studentId);
        console.log('교사 ID:', teacherId);

        // 선택된 학생의 성적을 가져옵니다
        const response = await axiosInst.get(`/api/student-diff/teacher/${teacherId}`);
        console.log('성적 응답:', response.data);
        
        // 선택된 학생의 성적만 필터링하고 필요한 데이터만 추출
        this.scores = response.data
          .filter(score => score.studentName === this.$route.query.studentName)
          .map(score => ({
            subjectId: this.getSubjectIdByUnitName(score.unitName),
            unitName: score.unitName,
            difficulty: score.difficulty
          }));
        
        // 과목 목록을 가져옵니다
        const subjectsResponse = await axiosInst.get('subjects');
        console.log('과목 응답:', subjectsResponse.data);
        this.subjects = subjectsResponse.data;
        
        this.updateChart();
      } catch (error) {
        console.error('성적 조회 실패:', error.response ? error.response.data : error.message);
        this.scores = []; // 에러 발생시 빈 배열로 초기화
        this.subjects = []; // 에러 발생시 빈 배열로 초기화
      }
    },
    getSubjectIdByUnitName(unitName) {
      // 단원 이름에 따라 과목 ID를 매핑
      if (unitName === '받아쓰기' || unitName === '읽기' || unitName === '맞춤법') return 12; // 국어
      if (unitName === '역사' || unitName === '정치' || unitName === '경제') return 13; // 사회
      if (unitName === '식물' || unitName === '동물' || unitName === '지구') return 14; // 과학
      if (unitName === '사칙연산' || unitName === '창의력' || unitName === '수식') return 15; // 수학
      return 0; // 기본값
    },
    updateChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const labels = this.filteredScores.map(score => `${this.getSubjectName(score.subjectId)}\n${score.unitName}`);
      const data = this.filteredScores.map(score => score.difficulty);
      
      const ctx = this.$refs.chartCanvas.getContext('2d');
      
      this.chart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [{
            label: '난이도',
            data: data,
            backgroundColor: '#748ffc',
            borderColor: '#5c7cfa',
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true,
              max: 5,
              ticks: {
                stepSize: 1,
                callback: function(value) {
                  return value;
                }
              },
              title: {
                display: true,
                text: '난이도'
              }
            },
            x: {
              title: {
                display: true,
                text: '과목-단원'
              }
            }
          },
          plugins: {
            legend: {
              display: false
            },
            tooltip: {
              enabled: true,
              callbacks: {
                label: function(context) {
                  return `난이도: ${context.raw}`;
                }
              }
            }
          }
        }
      });
    },
    getDifficultyClass(difficulty) {
      return `difficulty-${difficulty}`;
    }
  },
  created() {
    this.fetchScores();
  },
  unmounted() {
    if (this.chart) {
      this.chart.destroy();
    }
  }
}
</script>

<style scoped>
.score-view {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f3f0ff;
  min-height: 100vh;
}

h1, h2 {
  text-align: center;
  color: #5f3dc4;
  margin-bottom: 30px;
  font-size: 2.2rem;
  font-weight: 600;
  text-shadow: 2px 2px 4px rgba(95, 61, 196, 0.1);
}

.content-wrapper {
  display: flex;
  gap: 30px;
  margin-bottom: 30px;
}

.score-table {
  flex: 1;
  overflow-x: auto;
}

.chart-container {
  flex: 1;
  padding: 20px;
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(95, 61, 196, 0.1);
  display: flex;
  flex-direction: column;
}

.chart-wrapper {
  height: 300px;
  position: relative;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(95, 61, 196, 0.1);
}

th, td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #e5dbff;
}

th {
  background-color: #7950f2;
  color: white;
}

th:first-child {
  border-top-left-radius: 16px;
}

th:last-child {
  border-top-right-radius: 16px;
}

tr:hover {
  background-color: #f8f6ff;
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
  margin: 20px auto;
}

.back-button:hover {
  background-color: #845ef7;
  transform: translateY(-2px);
  box-shadow: 0 6px 15px rgba(151, 117, 250, 0.25);
}

@media (max-width: 768px) {
  .content-wrapper {
    flex-direction: column;
  }

  h1 {
    font-size: 1.8rem;
  }

  .chart-container {
    margin-top: 20px;
  }
}

.filter-section {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-end;
  padding: 0 20px;
}

.subject-filter {
  padding: 10px 15px;
  font-size: 1rem;
  border: 2px solid #7950f2;
  border-radius: 8px;
  background-color: white;
  color: #5f3dc4;
  cursor: pointer;
  transition: all 0.2s ease;
}

.subject-filter:hover {
  border-color: #5f3dc4;
  box-shadow: 0 2px 6px rgba(95, 61, 196, 0.2);
}

.subject-filter:focus {
  outline: none;
  border-color: #5f3dc4;
  box-shadow: 0 2px 8px rgba(95, 61, 196, 0.3);
}

.difficulty-1 {
  background-color: #e9ecef;
  color: #495057;
  font-weight: bold;
  border-radius: 4px;
  padding: 4px 8px;
}

.difficulty-2 {
  background-color: #d0ebff;
  color: #1971c2;
  font-weight: bold;
  border-radius: 4px;
  padding: 4px 8px;
}

.difficulty-3 {
  background-color: #d3f9d8;
  color: #2b8a3e;
  font-weight: bold;
  border-radius: 4px;
  padding: 4px 8px;
}

.difficulty-4 {
  background-color: #fff3bf;
  color: #e67700;
  font-weight: bold;
  border-radius: 4px;
  padding: 4px 8px;
}

.difficulty-5 {
  background-color: #ffe3e3;
  color: #c92a2a;
  font-weight: bold;
  border-radius: 4px;
  padding: 4px 8px;
}
</style> 