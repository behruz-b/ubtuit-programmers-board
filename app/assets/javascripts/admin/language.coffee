$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =
    send: '/create'
    get: '/get'
    delete: '/delete'
    update: '/update'

  vm = ko.mapping.fromJS
    nameLanguage: ''
    getListLanguage: []
    idLanguage: 0
    logoLanguage: ''

  handleError = (error) ->
    if error.status is 500 or (error.status is 400 and error.responseText)
      toastr.error(error.responseText)
    else
      toastr.error('Something went wrong! Please try again.')

  vm.onSubmit = ->
    toastr.clear()
    if (!vm.nameLanguage())
      toastr.error("Please enter a name")
      return no
    else if(!vm.logoLanguage())
      toastr.error("Please enter a logo")
      return no
    else
      data =
        name: vm.nameLanguage()
        logo: vm.logoLanguage()
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