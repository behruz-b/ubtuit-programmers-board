$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =
    addUser: '/createUser'
    addLanguage: 'createLanguage'


  defaultUserdata =
    firstName: ''
    lastName: ''
    login: ''
    password: ''
    photo: ''

  defaultLanguagedata =
    id: 0
    name: ''
    logo: ''

  vm = ko.mapping.fromJS
    user: defaultUserdata
  language: defaultLanguagedata

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
      toastr.error("Please enter a logo")
      return no
    else
      data =

      $.ajax
        url: apiUrl.send
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
        .fail handleError
        .done (response) ->
        toastr.success(response)



  ko.applyBindings {vm}