import { createRouter, createWebHistory } from 'vue-router'
import BlocksView from '../views/BlocksView.vue'
import MinersView from '../views/MinersView.vue'
import StatsView from '../views/StatsView.vue'
import InfoView from '../views/InfoView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/miners',
      name: 'miners',
      component: MinersView
    },
    {
      path: '/blocks',
      name: 'blocks',
      component: BlocksView,
      alias: '/'
    },
    {
      path: '/stats',
      name: 'stats',
      component: StatsView
    },
    {
      path: '/info',
      name: 'info',
      component: InfoView
    }
  ]
})

export default router
