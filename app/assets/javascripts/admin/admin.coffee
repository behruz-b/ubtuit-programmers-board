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

  defaultDirectionData =
    direction: ''

  vm = ko.mapping.fromJS
    user: defaultUserdata
    language: defaultLanguageData
    direction: defaultDirectionData

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

  vm.addDirection = ->
    toastr.clear()
    if (!vm.direction.name())
      toastr.error("Please enter a Direction Name")
      return no
    else
      data = ko.mapping.toJS(vm.direction())
      $.ajax
        url: apiUrl.addDirection
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
      .fail handleError
      .done (response) ->
        toastr.success(response)



  ko.applyBindings {vm}