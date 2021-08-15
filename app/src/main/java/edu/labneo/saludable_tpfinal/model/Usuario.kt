package edu.labneo.saludable_tpfinal.model
//data class de los usuarios
data class Usuario(var id: Int =0, val nombre: String, val apellido: String, val tipo_doc: String, val doc: String, val fecha_nac: String, val
    sexo: String, val localidad: String, val usuario: String, val pass:String, val tratamiento:String)