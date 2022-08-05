
Vue.component('task-form',{
  template: `<div v-if="$store.task.id"><div class="card taskform">
    <h5 class="card-title">{{$store.task.name}}</h5>
	<div id="task-form"></div>
	<div class="ms-2 me-2 mb-2 d-flex justify-content-between">
	  <button v-if="$store.task.assignee" type="button" class="btn btn-primary" @click="unclaim()">{{ $t("message.unclaim") }}</button>
	  <button v-if="!$store.task.assignee" type="button" class="btn btn-primary" @click="claim()">{{ $t("message.claim") }}</button>
	  <button :disabled="!$store.task.assignee || $store.task.assignee!=$store.user.name" type="button" class="btn btn-primary" @click="submit()">{{ $t("message.submit") }}</button>
	</div>
	<adhoc-task-modal></adhoc-task-modal>
	
	</div><button v-if="this.$store.task.name=='Answer to the customer'" type="button" class="btn btn-primary" @click="adhocTask()">Adhoc task</button></div>`,
  data() {
    return {
	  form: null
	}
  },
  methods: {
    adhocTask() {
	  let modal = new bootstrap.Modal(document.getElementById('adhoc-task-modal'), {});
	  modal.show();
	},
    claim() {
	  axios.get('/tasks/'+this.$store.task.id+'/claim').then(response => {
        this.$store.task.assignee=this.$store.user.name;
      }).catch(error => {
        alert(error.message);
      })
      this.form.setProperty('readOnly', false);
    },
    unclaim() {
	  axios.get('/tasks/'+this.$store.task.id+'/unclaim/').then(response => {
        this.$store.task.assignee=null;
	  }).catch(error => {
	    alert(error.message); 
	  })
	  this.form.setProperty('readOnly', true);
	},
	submit() {
	  this.form.validate();
	  let errors = [];
	  for (const field in this.form._state.errors) {
	    if (this.form._state.errors[field].length>0) {
	      Array.prototype.push.apply(errors, this.form._state.errors[field]);
	    }
	  }
	  if (errors.length==0) {
	    axios.post('/tasks/'+this.$store.task.id, this.form._getState().data).then(response => {
          let i =0;
          for(;i<this.$store.tasks.length;i++) {
            if (this.$store.tasks[i].id==this.$store.task.id) {
              this.$store.tasks.splice(i,1);
              break;
            }
	      }
          if(i<this.$store.tasks.length) {
            this.$store.task = this.$store.tasks[i];
          } else {
            this.$store.task = {
              id: null,
              name: null,
              creationTime: "1970-01-01"
            };
            this.form = null;
          }
        }).catch(error => {
          alert(error.message);
        })
      }
    },
    mountForm() {
      if (!this.$store.task.id) {
        this.form = null;
      } else {
        let url = '/forms/'+this.$store.task.processDefinitionId+'/'+this.$store.task.formKey;
        axios.get(url).then(response => {
          let schema = response.data; 
          if (this.form==null) {
            this.form = new FormViewer.Form({
              container: document.querySelector('#task-form')
            });
          }
          this.form.importSchema(schema, this.$store.task.variables);
          if (!this.$store.task.assignee) {
            this.form.setProperty('readOnly', true);
          }
        }).catch(error => {
          alert(error.message); 
        })
      }
    }
  },
  updated() {
    this.mountForm();
  },
  mounted() {
    this.mountForm();
  }
});


Vue.component('adhoc-task-modal',{
  template: `<div class="modal fade" id="adhoc-task-modal" ref="adhoc-task-modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header bg-secondary text-light">
        <h5 class="modal-title">Add an adhoc task</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="input-group mb-3">
		  <span class="input-group-text">Action</span>
		  <select class="form-control" id="actionType" v-model="data.lastAdhocTaskType">
		    <option value="requestDocument">Request a document</option>
		    <option value="cancelCard">Cancel card</option>
		    <option value="revertMovment">Revert movement</option>
		  </select>
		</div>
        <div class="input-group mb-3" v-if="data.lastAdhocTaskType=='requestDocument'">
		  <span class="input-group-text">Document type</span>
		  <input type="text" class="form-control" placeholder="Document type" v-model="data.documentType">
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" @click="addAction" data-bs-dismiss="modal">Add action</button>
        <button class="btn btn-link" data-bs-dismiss="modal">Cancel</button>
      </div>
    </div>
  </div>
</div>`,
  data() {
    return {
		data: {
		  lastAdhocTaskType:"requestDocument",
		  documentType:"ID card"
		}
	}
  },
  methods: {
	  addAction() {
		this.data.initiator = this.$store.task.variables.initiator;
		
		axios.post('/tasks/adhoc', this.data).then(response => {
			console.log(response.data); 	
		}).catch(error => {
			console.log(error.message); 
		})
	  }
  }
});
	