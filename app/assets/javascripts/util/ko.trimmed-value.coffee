ko.bindingHandlers.trimmedValue =
  init: (element, valueAccessor) ->
    accessor = valueAccessor()
    $element = $(element)
    $element.on 'change keyup', ->
      trimmed = $.trim($element.val())
      #$(element).val trimmed
      accessor trimmed

  update: (element, valueAccessor) ->
    accessor = valueAccessor()
    val = ko.utils.unwrapObservable(accessor)
    trimmed = $.trim(val)
    if !trimmed
      $(element).val trimmed
    accessor trimmed
