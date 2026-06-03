<template>
  <div class="container">
    <header class="page-header">
      <h1>⚙️ Lode Manutenções</h1>
      <button @click="abrirModal(null)" class="btn-primary">+ Novo Equipamento</button>
    </header>

    <!-- Filtros -->
    <div class="filtros">
      <input
        v-model="filtroBusca"
        placeholder="🔍 Buscar por nome..."
        class="input-busca"
      />
      <select v-model="filtroStatus" class="select-status">
        <option value="">Todos os status</option>
        <option value="OPERACIONAL">Operacional</option>
        <option value="MANUTENCAO_NECESSARIA">Manutenção Necessária</option>
        <option value="CRITICO">Crítico</option>
      </select>
    </div>

    <!-- Cards -->
    <div v-if="equipamentosFiltrados.length" class="grid-cards">
      <EquipmentCard
        v-for="eq in equipamentosFiltrados"
        :key="eq.id"
        :equipment="eq"
        @editar="abrirModal"
        @deletar="confirmarDelete"
      />
    </div>
    <p v-else class="sem-dados">Nenhum equipamento encontrado.</p>

    <!-- Modal -->
    <EquipmentModal
      v-if="modalAberto"
      :equipment="equipamentoSelecionado"
      @salvar="salvar"
      @fechar="modalAberto = false"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { equipmentApi } from '@/api/Equipment.js'
import EquipmentCard from '@/components/EquipmentCard.vue'
import EquipmentModal from '@/components/EquipmentModal.vue'

const equipamentos        = ref([])
const filtroBusca         = ref('')
const filtroStatus        = ref('')
const modalAberto         = ref(false)
const equipamentoSelecionado = ref(null)

// Filtro em tempo real por nome (via computed)
const equipamentosFiltrados = computed(() =>
  equipamentos.value.filter(eq => {
    const nomeOk   = (eq.name ?? '').toLowerCase().includes(filtroBusca.value.toLowerCase())
    const statusOk = !filtroStatus.value || eq.status === filtroStatus.value
    return nomeOk && statusOk
  })
)

async function carregar() {
  const res = await equipmentApi.findAll()
  equipamentos.value = res.data
}

function abrirModal(eq) {
  equipamentoSelecionado.value = eq ? { ...eq } : null
  modalAberto.value = true
}

async function salvar(dados) {
  if (dados.id) {
    await equipmentApi.update(dados.id, dados)
  } else {
    await equipmentApi.create(dados)
  }
  modalAberto.value = false
  await carregar()
}

async function confirmarDelete(id) {
  if (confirm('Confirmar exclusão do equipamento?')) {
    await equipmentApi.delete(id)
    await carregar()
  }
}

onMounted(carregar)
</script>

<style scoped>
.container    { max-width: 1200px; margin: 0 auto; padding: 2rem; }
.page-header  { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; }
.filtros      { display: flex; gap: 1rem; margin-bottom: 1.5rem; }
.input-busca  { flex: 1; padding: 0.6rem 1rem; border: 1px solid #ddd; border-radius: 8px; font-size: 1rem; }
.select-status{ padding: 0.6rem; border: 1px solid #ddd; border-radius: 8px; }
.grid-cards   { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 1.2rem; }
.btn-primary  { background: #3b82f6; color: white; border: none; padding: 0.6rem 1.2rem; border-radius: 8px; cursor: pointer; font-size: 1rem; }
.btn-primary:hover { background: #2563eb; }
.sem-dados    { text-align: center; color: #999; margin-top: 3rem; font-size: 1.1rem; }
</style>
