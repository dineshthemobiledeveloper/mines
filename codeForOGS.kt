//Shared Prefrence Codes

val sharedPreference:SharedPreference=SharedPreference(this)

sharedPreference.save("name",name)
sharedPreference.save("email",email)

sharedPreference.getValueString("name")

sharedPreference.clearSharedPreference()
