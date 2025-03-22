<template>
  <div class="student-manage">
    <div class="content-container">
      <h1>학생 관리</h1>
      <div class="add-student-form">
        <input 
          type="text" 
          v-model="newStudentName" 
          placeholder="학생 이름을 입력하세요"
          class="student-input"
          @keyup.enter="addStudent"
        >
        <button @click="addStudent" class="add-button">
          <span class="button-icon">+</span>
          등록
        </button>
      </div>
      <div class="student-table">
        <table>
          <thead>
            <tr>
              <th>순번</th>
              <th>교사 이름</th>
              <th>학생 이름</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(student, index) in students" :key="student.studentId">
              <td>{{ index + 1 }}</td>
              <td>{{ student.teacher.teacherName }}</td>
              <td>{{ student.studentName }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axiosInst from '@/axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'StudentManage',
  setup() {
    const students = ref([]);
    const newStudentName = ref('');

    const fetchStudents = async () => {
      const teacherId = localStorage.getItem('teacherId');
      if (teacherId) {
        try {
          const res = await axiosInst.get(`api/students/teacher/${teacherId}`);
          students.value = res.data;
        } catch (error) {
          console.error('Error fetching students:', error);
        }
      }
    };

    const addStudent = async () => {
      const teacherId = localStorage.getItem('teacherId');
      if (teacherId) {
        try {
          const res = await axiosInst.post('api/students', {
            studentName: newStudentName.value,
            teacher: { teacherId: teacherId }
          });
          students.value.push(res.data);
          newStudentName.value = '';
        } catch (error) {
          console.error('Error adding student:', error);
        }
      }
    };

    onMounted(() => {
      fetchStudents();
    });

    return {
      students,
      newStudentName,
      addStudent
    };
  }
};
</script>

<style scoped>
.student-manage {
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 2rem;
}

.content-container {
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  color: #343a40;
  margin-bottom: 2rem;
  font-size: 2rem;
  font-weight: 600;
  text-align: center;
}

.add-student-form {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.student-input {
  flex: 1;
  padding: 0.75rem 1rem;
  font-size: 1rem;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  color: #495057;
  transition: all 0.2s ease;
}

.student-input:focus {
  outline: none;
  border-color: #228be6;
  box-shadow: 0 0 0 3px rgba(34, 139, 230, 0.1);
}

.add-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  font-weight: 500;
  color: white;
  background-color: #228be6;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.add-button:hover {
  background-color: #1c7ed6;
}

.add-button:active {
  transform: translateY(1px);
}

.button-icon {
  font-size: 1.2rem;
  font-weight: bold;
}

.student-table {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #e9ecef;
}

th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #495057;
}

tr:last-child td {
  border-bottom: none;
}

tbody tr:hover {
  background-color: #f8f9fa;
}

@media (max-width: 768px) {
  .add-student-form {
    flex-direction: column;
  }

  .add-button {
    width: 100%;
    justify-content: center;
  }

  th, td {
    padding: 0.75rem;
  }
}
</style>