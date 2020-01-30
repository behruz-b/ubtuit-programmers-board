$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =
    addUser: '/createUser'
    addLanguage: '/createLanguage'


  defaultUserdata =
    firstName: ''
    lastName: ''
    login: ''
    password: ''
    photo: ''

  defaultLanguageData =
    name: ''
    logo: ''

  vm = ko.mapping.fromJS
    user: defaultUserdata
    language: defaultLanguageData

  handleError = (error) ->
    if error.status is 500 or (error.status is 400 and error.responseText)
      toastr.error(error.responseText)
    else
      toastr.error('Something went wrong! Please try again.')

  vm.addLanguage = ->
    toastr.clear()
    if (!vm.language.name())
      toastr.error("Please enter a name")
      return no
    else if(!vm.language.logo())
      toastr.error("Please upload a logo the Language")
      return no
    else
      data = ko.mapping.toJS(vm.language())
      $.ajax
        url: apiUrl.addLanguage
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
      .fail handleError
      .done (response) ->
        toastr.success(response)



  ko.applyBindings {vm}