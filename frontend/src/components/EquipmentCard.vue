<template>
  <div class="card" :class="statusClass">
    <div class="card-header">
      <span class="status-badge">{{ equipment.status }}</span>
      <div class="actions">
        <button @click="$emit('editar', equipment)" class="btn-icon">✏️</button>
        <button @click="$emit('deletar', equipment.id)" class="btn-icon">🗑️</button>
      </div>
    </div>
    <h3>{{ equipment.name }}</h3>
    <p><strong>Tipo:</strong> {{ equipment.type }}</p>
    <p><strong>Instalação:</strong> {{ formatarData(equipment.installationDate) }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  equipment: { type: Object, required: true }
})

defineEmits(['editar', 'deletar'])

const statusClass = {
  'status-operacional':   props.equipment.status === 'Operacional',
  'status-manutencao':    props.equipment.status === 'Manutenção Necessária',
  'status-critico':       props.equipment.status === 'Crítico'
}

function formatarData(data) {
  if (!data) return '-'

  if (Array.isArray(data)) {
    const [ano, mes, dia] = data
    return `${String(dia).padStart(2, '0')}/${String(mes).padStart(2, '0')}/${ano}`
  }

  const [ano, mes, dia] = data.split('-')
  return `${dia}/${mes}/${ano}`
}
</script>

<style scoped>
.card {
  border-radius: 10px;
  padding: 1rem;
  border-left: 6px solid #ccc;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: transform 0.2s;
}
.card:hover { transform: translateY(-3px); }
.card-header { display: flex; justify-content: space-between; align-items: center; }

/* Status colors */
.status-operacional  { border-left-color: #22c55e; }
.status-manutencao   { border-left-color: #f59e0b; }
.status-critico      { border-left-color: #ef4444; }

.status-badge {
  font-size: 0.75rem;
  font-weight: bold;
  padding: 2px 8px;
  border-radius: 999px;
  background: #f1f5f9;
}
.status-operacional .status-badge { background: #dcfce7; color: #166534; }
.status-manutencao  .status-badge { background: #fef3c7; color: #92400e; }
.status-critico     .status-badge { background: #fee2e2; color: #991b1b; }

.btn-icon { background: none; border: none; cursor: pointer; font-size: 1rem; }
h3 { margin: 0.5rem 0 0.3rem; font-size: 1.1rem; }
p  { margin: 0.2rem 0; color: #555; font-size: 0.9rem; }
</style>
