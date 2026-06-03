import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

export const equipmentApi = {
  findAll: (name = '') =>
    api.get('/equipamentos', { params: name ? { name } : {} }),

  finById: (id) =>
    api.get(`/equipamentos/${id}`),

  create: (data) =>
    api.post('/equipamentos', data),

  update: (id, data) =>
    api.put(`/equipamentos/${id}`, data),

  delete: (id) =>
    api.delete(`/equipamentos/${id}`)
}
