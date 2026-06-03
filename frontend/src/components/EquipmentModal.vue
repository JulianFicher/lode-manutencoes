<template>
  <div class="overlay" @click.self="$emit('fechar')">
    <div class="modal">
      <h2>{{ form.id ? 'Editar' : 'Novo' }} Equipamento</h2>

      <label>Nome</label>
      <input v-model="form.name" placeholder="Ex: Bomba Principal"/>

      <label>Tipo</label>
      <select v-model="form.type">
        <option value="">Selecione...</option>
        <option value="BOMBA">Bomba</option>
        <option value="PAINEL">Painel</option>
        <option value="TUBULACAO">Tubulação</option>
        <option value="SENSOR">Sensor</option>
      </select>

      <label>Data de Instalação</label>
      <div class="date-inputs">
        <input v-model="dia" type="number" placeholder="DD"   min="1"    max="31"   />
        <span>/</span>
        <input v-model="mes" type="number" placeholder="MM"   min="1"    max="12"   />
        <span>/</span>
        <input v-model="ano" type="number" placeholder="AAAA" min="2000" max="2100" />
        <input
          ref="datePickerRef"
          type="date"
          class="date-picker-hidden"
          @change="preencherDataPeloCalendario"
        />
        <span class="calendar-icon" @click="datePickerRef.showPicker()">📅</span>
      </div>

      <label>Status</label>
      <select v-model="form.status">
        <option value="">Selecione...</option>
        <option value="OPERACIONAL">Operacional</option>
        <option value="MANUTENCAO_NECESSARIA">Manutenção Necessária</option>
        <option value="CRITICO">Crítico</option>
      </select>

      <div class="modal-actions">
        <button @click="$emit('fechar')" class="btn-cancelar">Cancelar</button>
        <button @click="confirm" class="btn-salvar">Salvar</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const props = defineProps({
  equipment: { type: Object, default: null }
})

const emit = defineEmits(['salvar', 'fechar'])

const datePickerRef = ref(null)
const dataInicial = props.equipment?.installationDate?.split('-') ?? []
const dia = ref(dataInicial[2] ?? '')
const mes = ref(dataInicial[1] ?? '')
const ano = ref(dataInicial[0] ?? '')

const form = reactive({
  id:               props.equipment?.id               ?? null,
  name:             props.equipment?.name             ?? '',
  type:             props.equipment?.type             ?? '',
  installationDate: props.equipment?.installationDate ?? '',
  status:           props.equipment?.status           ?? ''
})

function preencherDataPeloCalendario(event) {
  const [anoValue, mesValue, diaValue] = event.target.value.split('-')
  ano.value = anoValue
  mes.value = mesValue
  dia.value = diaValue
}

function confirm() {
  if (!form.name || !form.type || !dia.value || !mes.value || !ano.value || !form.status) {
    alert('Preencha todos os campos!')
    return
  }
  const diaPad = String(dia.value).padStart(2, '0')
  const mesPad = String(mes.value).padStart(2, '0')
  form.installationDate = `${ano.value}-${mesPad}-${diaPad}`
  emit('salvar', { ...form })
}
</script>

<style scoped>
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.modal {
  background: white;
  border-radius: 12px;
  padding: 2rem;
  width: 420px;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

h2 {
  margin: 0 0 1rem;
}

label {
  font-size: 0.85rem;
  font-weight: 600;
  color: #374151;
}

input, select {
  padding: 0.6rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  width: 100%;
  box-sizing: border-box;
}

.modal-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1rem;
}

.btn-cancelar {
  padding: 0.6rem 1.2rem;
  background: #e5e7eb;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.btn-salvar {
  padding: 0.6rem 1.2rem;
  background: #3b82f6;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.date-inputs {
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

.date-inputs input {
  text-align: center;
}

.date-inputs input:nth-child(1) {
  width: 60px;
}

/* DD */
.date-inputs input:nth-child(3) {
  width: 60px;
}

/* MM */
.date-inputs input:nth-child(5) {
  width: 80px;
}

.date-picker-hidden {
  position: absolute;
  opacity: 0;
  width: 30px;
  height: 30px;
  cursor: pointer;
}

.calendar-icon {
  cursor: pointer;
  font-size: 1.3rem;
}

/* AAAA */
</style>
