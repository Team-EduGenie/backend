import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import StudentManage from '../views/StudentManage.vue'
import TeacherLogin from '../views/TeacherLogin.vue'
import TeacherMenu from '../views/TeacherMenu.vue'
import StudentMenu from '@/views/StudentMenu.vue'
import StudentLogin from '@/views/StudentLogin.vue'
import Unit from '@/views/Unit.vue'
import Quiz from '@/views/Quiz.vue'
import TeacherScore from '../views/TeacherScore.vue'
import TeacherSubject from '../views/TeacherSubject.vue'
import CustomQuizMenu from '../views/CustomQuizMenu.vue'
import CustomQuiz from '../views/CustomQuiz.vue'
import Subject from '../views/Subject.vue'
import CustomPromptQuiz from '../views/CustomPromptQuiz.vue'
import TeacherStudentSelect from '../views/TeacherStudentSelect.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/students',
      name: 'students',
      component: StudentManage
    },
    {
      path: '/teacherlogin',
      name: 'teacherlogin',
      component: TeacherLogin
    },
    {
      path: '/teachermenu',
      name: 'teachermenu',
      component: TeacherMenu
    },
    {
      path: '/studentlogin',
      name: 'studentlogin',
      component: StudentLogin
    },
    {
      path: '/studentmenu',
      name: 'studentmenu',
      component: StudentMenu
    },
    {
      path: '/subject',
      name: 'subject',
      component: Subject
    },
    {
      path: '/unit',
      name: 'unit',
      component: Unit
    },
    {
      path: '/quiz',
      name: 'quiz',
      component: Quiz
    },
    {
      path: '/teachersubject',
      name: 'teachersubject',
      component: TeacherSubject
    },
    {
      path: '/teacherscore',
      name: 'teacherscore',
      component: TeacherScore
    },
    {
      path: '/custom-quiz-menu',
      name: 'custom-quiz-menu',
      component: CustomQuizMenu
    },
    {
      path: '/custom-quiz',
      name: 'custom-quiz',
      component: CustomQuiz
    },
    {
      path: '/custom-prompt-quiz',
      name: 'custom-prompt-quiz',
      component: CustomPromptQuiz
    },
    {
      path: '/teacherstudentselect',
      name: 'teacherstudentselect',
      component: TeacherStudentSelect
    }
  ]
})

export default router

//path는 주소창에 입력하는 경로
//name은 라우터의 이름
//component는 해당 경로에 매핑될 컴포넌트